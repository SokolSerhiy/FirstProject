package ua.http.impl;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.annotation.qualifier.HttpQualifier;
import ua.annotation.qualifier.ObjectMapperQualifier;
import ua.http.Http;
import ua.http.Response;

@Service
@HttpQualifier
public class ApacheHttp implements Http {
	
	private final HttpClient httpClient;
	
	private final ObjectMapper mapper;
	
	@Autowired
	@ObjectMapperQualifier
	public ApacheHttp(ObjectMapper mapper) {
		httpClient = HttpClientBuilder.create().build();
		this.mapper = mapper;
	}

	@Override
	public Response get(String url) {
		return execute(new HttpGet(url));
	}

	@Override
	public Response get(String url, Map<String, String> headers) {
		HttpGet get = new HttpGet(url);
		addHeaders(get, headers);
		return execute(get);
	}

	@Override
	public Response post(String url) {
		return execute(new HttpPost(url));
	}

	@Override
	public Response post(String url, Map<String, String> headers) {
		HttpPost post = new HttpPost(url);
		addHeaders(post, headers);
		return execute(post);
	}

	@Override
	public Response post(String url, Object json){
		try{
			StringEntity input = new StringEntity(map(json));
			input.setContentType("application/json");
			HttpPost post = new HttpPost(url);
			post.setEntity(input);
			return execute(post);
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Response post(String url, Map<String, String> headers, Object json) {
		try{
			StringEntity input = new StringEntity(map(json));
			input.setContentType("application/json");
			HttpPost post = new HttpPost(url);
			post.setEntity(input);
			addHeaders(post, headers);
			return execute(post);
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Response execute(HttpRequestBase request){
		try{
			return new SuccessResponse(httpClient.execute(request));
		}catch (IOException e) {
			return new FailResponse();
		}
	}
	
	private void addHeaders(HttpRequestBase request, Map<String, String> headers){
		getHeaders(headers).forEach(header->request.addHeader(header));
	}
	
	private List<Header> getHeaders(Map<String, String> headers){
		return headers.entrySet().stream()
				.map(this::getHeader)
				.collect(Collectors.toList());
	}

	private Header getHeader(Map.Entry<String, String> entry){
		return new BasicHeader(entry.getKey(), entry.getValue());
	}
	
	private String map(Object o){
		if(o instanceof String) return (String)o;
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
