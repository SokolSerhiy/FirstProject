package ua.http.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.annotation.qualifier.AuthHttpQualifier;
import ua.annotation.qualifier.HttpQualifier;
import ua.annotation.qualifier.ObjectMapperQualifier;
import ua.http.Http;
import ua.http.Response;
import ua.model.request.AuthRequest;
import ua.model.resource.EmptyResource;
import ua.model.resource.TokenResource;
import ua.smart.SmartUrl;

@Service
@AuthHttpQualifier
public class AuthApacheHttp implements Http, Runnable{
	
	private static final long SEC_BEFORE_EXPIRATION = 5000;
	
	private static final String HEADER = "X-AUTH-HEADER";
	
	private final Http http;
	
	private volatile String token;
	
	private long delay;
	
	private final ObjectMapper objectMapper;
	
	private final SmartUrl smartUrl;
	
	@Value("${login}")
	private String login;
	
	@Value("${password}")
	private String password;

	@Autowired
	public AuthApacheHttp(@HttpQualifier Http http, @ObjectMapperQualifier ObjectMapper objectMapper, SmartUrl smartUrl) {
		this.http = http;
		this.objectMapper = objectMapper;
		this.smartUrl = smartUrl;
	}
	
	@PostConstruct
	public void init(){
		Thread daemon = new Thread(this);
		System.out.println("Starting authentication...");
		authentication(login, password);
		System.out.println("End authentication...");
		setDelay();
		daemon.setDaemon(true);
		System.out.println("Starting refreshing...");
		daemon.start();
	}
	
	private void delay(){
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
		}
	}
	
	private void setDelay(){
		long exp = getExpiration();
		delay = exp-System.currentTimeMillis()-SEC_BEFORE_EXPIRATION;
	}
	
	private long getExpiration(){
		try {
			String json = new String(Base64.getDecoder().decode(token.split("\\.")[1]), "UTF-8");
			return (long) objectMapper.readValue(json, Map.class).get("expiration");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void authentication(String login, String password){
		EmptyResource json = get(BASE_URL).json(EmptyResource.class);
		smartUrl.findLoginUrl(json);
		TokenResource resource = post(smartUrl.getLogin(), new AuthRequest(login, password)).json(TokenResource.class);
		token = resource.getToken();
		smartUrl.findRefreshUrl(resource);
		smartUrl.findGameUrl(resource);
	}
	
	public Response get(String url) {
		return http.get(url, getAuthHeaders());
	}

	public Response get(String url, Map<String, String> headers) {
		return http.get(url, putAuthHeaders(headers));
	}

	public Response post(String url) {
		return http.post(url, getAuthHeaders());
	}

	public Response post(String url, Map<String, String> headers) {
		return http.post(url, putAuthHeaders(headers));
	}

	public Response post(String url, Object json) {
		return http.post(url, getAuthHeaders(), json);
	}

	public Response post(String url, Map<String, String> headers, Object json) {
		return http.post(url, putAuthHeaders(headers), json);
	}
	
	private Map<String, String> putAuthHeaders(Map<String, String> headers){
		if(token!=null) headers.put(HEADER, token);
		return headers;
	}
	
	private Map<String, String> getAuthHeaders(){
		return putAuthHeaders(new HashMap<>());
	}

	@Override
	public void run() {
		while(true){
			delay();
			Map<String, String> headers = getAuthHeaders();
			System.out.println("Start refresh");
			TokenResource resource = get(smartUrl.getRefresh(), headers).json(TokenResource.class);
			System.out.println("End refresh");
			token = resource.getToken();
			smartUrl.findRefreshUrl(resource);
			System.out.println("Token refreshed");
			setDelay();
		}
	}
}