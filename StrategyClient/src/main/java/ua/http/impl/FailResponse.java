package ua.http.impl;

import ua.http.Response;

public class FailResponse implements Response{

	@Override
	public String text() {
		return null;
	}

	@Override
	public <T> T json(Class<T> dataType) {
		return null;
	}

	@Override
	public void close() {
		
	}
}
