package ua.lviv.lgs.model;

import org.springframework.hateoas.ResourceSupport;

public class TokenResource extends ResourceSupport{

	private String token;

	public TokenResource(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
