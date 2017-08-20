package ua.http;

public interface Response {

	<T> T json(Class<T> dataType);
	
	String text();
	
	void close();
}
