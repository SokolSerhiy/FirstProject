package ua.lviv.lgs.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.lviv.lgs.model.ChangePasswordRequest;
import ua.lviv.lgs.model.ProfilePhotoRequest;
import ua.lviv.lgs.model.UserData;
import ua.lviv.lgs.security.TokenUtils;
import ua.lviv.lgs.service.UserService;

@RestController
public class UserController {

	private final UserService service;
	
	private final UserDetailsService userDetailsService;
	
	private final TokenUtils tokenUtils;
	
	@Autowired
	public UserController(UserService service, UserDetailsService userDetailsService, TokenUtils tokenUtils) {
		this.service = service;
		this.userDetailsService = userDetailsService;
		this.tokenUtils = tokenUtils;
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/current/user")
	public ResponseEntity<UserData> getCurrentUser(Principal principal){
		return ResponseEntity.ok(service.findByEmail(principal.getName()));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/profile/photo")
	public ResponseEntity<?> savePhoto(@RequestBody ProfilePhotoRequest request, Principal principal) {
		String photoUrl = service.savePhoto(request, principal.getName());
		if (photoUrl!=null) return ResponseEntity.ok(photoUrl);
		return ResponseEntity.status(400).build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/validate", params="password")
	public ResponseEntity<Boolean> validatePassword(@RequestParam String password, Principal principal){
		boolean result = service.passwordMatch(password, principal.getName());
		return ResponseEntity.ok(result);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/change/password")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Principal principal){
		service.changePassword(request, principal.getName());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(principal.getName());
		String token = this.tokenUtils.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}
	
	@GetMapping(value="/validate", params="registeredEmail")
	public ResponseEntity<Boolean> validateEmail(@RequestParam String registeredEmail, Principal principal){
		boolean result = service.existsByUsername(registeredEmail, principal.getName());
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/change/data")
	public ResponseEntity<String> changeData(@RequestBody UserData data, Principal principal){
		service.changeData(data, principal.getName());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(data.getEmail());
		String token = this.tokenUtils.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}
}
