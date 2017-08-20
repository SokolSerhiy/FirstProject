package ua.lviv.lgs.controller;
import static ua.lviv.lgs.service.LinkBuilder.*;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.lviv.lgs.model.GameResource;
import ua.lviv.lgs.service.PlaceService;

@RestController
@RequestMapping("/game")
public class GameController {
	
	private final PlaceService service;
	
	@Autowired
	public GameController(PlaceService service) {
		this.service = service;
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GameResource> base(Principal principal){
		GameResource resource = service.getResource(principal.getName());
		build(resource);
		return ResponseEntity.ok(resource);
	}
	
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> save(@RequestBody GameResource resource, Principal principal){
		service.update(resource, principal.getName());
		return ResponseEntity.ok().build();
	}
}
