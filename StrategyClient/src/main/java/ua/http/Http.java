package ua.http;

import java.util.Map;

public interface Http {
	
	public static final String BASE_URL = "http://localhost:8080/api/";

	Response get(String url);
	
	Response get(String url, Map<String, String> headers);
	
	Response post(String url);
	
	Response post(String url, Map<String, String> headers);
	
	Response post(String url, Object json);
	
	Response post(String url, Map<String, String> headers, Object json);
}
