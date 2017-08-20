package ua.lviv.lgs.service;

import ua.lviv.lgs.model.GameResource;

public interface PlaceService {

	GameResource getResource(String email);

	void update(GameResource resource, String email);
}
