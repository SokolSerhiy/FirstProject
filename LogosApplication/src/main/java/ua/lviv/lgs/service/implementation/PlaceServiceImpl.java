package ua.lviv.lgs.service.implementation;
import static ua.lviv.lgs.mapper.PlaceMapper.toGameResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.lviv.lgs.entity.game.Place;
import ua.lviv.lgs.model.GameResource;
import ua.lviv.lgs.repository.PlaceRepository;
import ua.lviv.lgs.repository.UserRepository;
import ua.lviv.lgs.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService{

	private final PlaceRepository repository;
	
	private final UserRepository userRepository;
	
	@Autowired
	public PlaceServiceImpl(PlaceRepository repository, UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}

	@Override
	public GameResource getResource(String email) {
		GameResource resource = repository.getResource(email);
		if(resource == null) {
			Place place = new Place(userRepository.findByEmail(email));
			repository.save(place);
			return toGameResource(place);
		}
		return resource;
	}

	@Override
	public void update(GameResource resource, String email) {
		System.out.println(resource);
		repository.update(resource, email);
	}
}
