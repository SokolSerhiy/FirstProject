package ua.lviv.lgs.service;
import static ua.lviv.lgs.entity.Role.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ua.lviv.lgs.entity.Role;
import ua.lviv.lgs.model.GameResource;
import ua.lviv.lgs.model.StartResource;
import ua.lviv.lgs.model.TokenResource;

public class LinkBuilder {
	
	private static final Map<Class<?>, Map<Role, Map<String, String>>> links = new HashMap<>();
	
	static {
		addResources();
		addAnonimous();
		addAdmin();
		addStudent();
		addTeacher();
		addUser();
	}
	
	private static void addResources() {
		links.put(StartResource.class, new HashMap<>());
		links.put(GameResource.class, new HashMap<>());
	}
	
	private static void addAnonimous() {
		links.get(StartResource.class).put(ROLE_ANONYMOUS, new HashMap<>());
		links.get(StartResource.class).get(ROLE_ANONYMOUS).put("login", "/auth/login");
		links.get(StartResource.class).get(ROLE_ANONYMOUS).put("registration", "/auth/registration");
		links.get(StartResource.class).get(ROLE_ANONYMOUS).put("self", "/");
	}
	
	private static void addAdmin() {
		addUrlProfile(ROLE_ADMIN);
		addUrlGame(ROLE_ADMIN);
	}
	
	private static void addStudent() {
		addUrlProfile(ROLE_STUDENT);
		addUrlGame(ROLE_STUDENT);
	}
	
	private static void addTeacher() {
		addUrlProfile(ROLE_TEACHER);
		addUrlGame(ROLE_TEACHER);
	}
	
	private static void addUser() {
		addUrlProfile(ROLE_USER);
		addUrlGame(ROLE_USER);
	}
	
	private static void addUrlGame(Role role) {
		links.get(GameResource.class).put(role, new HashMap<>());
		links.get(GameResource.class).get(role).put("self", "/game");
	}
	
	private static void addUrlProfile(Role role) {
		links.get(StartResource.class).put(role, new HashMap<>());
		links.get(StartResource.class).get(role).put("profile", "/profile");
	}
	
	public static void build(ResourceSupport resource){
		GrantedAuthority authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
		Role role = Role.valueOf(authority.getAuthority());
		String host = getHost();
		for(Map.Entry<String, String> link : links.get(resource.getClass()).get(role).entrySet()) {
			resource.add(new Link(host+link.getValue(), link.getKey()));
		}
	}
	
	public static void buildTokenResLinks(TokenResource resource) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String protocol = request.getProtocol();
		if(protocol.startsWith("HTTPS")) {
			protocol = protocol.substring(0, 5);
		}else {
			protocol = protocol.substring(0, 4);
		}
		String host = getHost();
		resource.add(new Link(host+"/auth/refresh", "refresh"));
		resource.add(new Link(host+"/game", "game"));
	}
	
	private static String getHost() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String protocol = request.getProtocol();
		if(protocol.startsWith("HTTPS")) {
			protocol = protocol.substring(0, 5);
		}else {
			protocol = protocol.substring(0, 4);
		}
		return protocol.toLowerCase()+"://"+request.getLocalName()+":"+request.getLocalPort()+request.getContextPath();
	}
}
