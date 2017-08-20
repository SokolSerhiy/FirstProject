package ua.smart;

import org.springframework.stereotype.Component;

import ua.model.resource.EmptyResource;
import ua.model.resource.Link;
import ua.model.resource.TokenResource;

@Component
public class SmartUrl {
	
	public static final String LOGIN_REL = "login";
	
	public static final String GAME_REL = "game";
	
	private static final String REFRESH_REL = "refresh";

	private String login;
	
	private String game;
	
	private String refresh;
	
	public void findRefreshUrl(TokenResource json){
		for(Link link : json.getLinks()){
			if(link.getRel().equals(REFRESH_REL)) refresh = link.getHref();
		}
	}
	
	public void findLoginUrl(EmptyResource json){
		for(Link link : json.getLinks()){
			if(link.getRel().equals(LOGIN_REL)) login = link.getHref();
		}
	}

	public void findGameUrl(TokenResource resource) {
		for(Link link : resource.getLinks()){
			if(link.getRel().equals(GAME_REL)) game = link.getHref();
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}
	
	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}
}
