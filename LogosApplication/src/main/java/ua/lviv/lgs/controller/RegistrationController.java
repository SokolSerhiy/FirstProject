package ua.lviv.lgs.controller;

import static ua.lviv.lgs.service.LinkBuilder.build;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.lviv.lgs.model.StartResource;
import ua.lviv.lgs.model.UserRegistration;
import ua.lviv.lgs.service.UserService;

@RestController
public class RegistrationController {

	private final UserService service;
	
	@Autowired
	public RegistrationController(UserService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public HttpEntity<StartResource> start(){
		StartResource resource = new StartResource();
		build(resource);
		return new HttpEntity<StartResource>(resource);
	}
	
	@GetMapping(value="/validate", params="email")
	public ResponseEntity<Boolean> validate(@RequestParam String email){
		boolean result = service.existsByUsername(email);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/registration")
	public ResponseEntity<?> register(@RequestBody UserRegistration registration){
		service.save(registration);
		return ResponseEntity.ok().build();
	}
}
