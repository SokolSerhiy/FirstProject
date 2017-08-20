package ua.lviv.lgs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.entity.game.Place;
import ua.lviv.lgs.model.GameResource;

public interface PlaceRepository extends JpaRepository<Place, String>{

	@Query("SELECT new ua.lviv.lgs.model.GameResource(p.lastDoneTask, p.food, p.wood, p.stone, p.iron, p.foodCollectors, p.woodCollectors, p.stoneCollectors, p.ironCollectors) FROM Place p JOIN p.user u WHERE u.email=?1")
	GameResource getResource(String email);
	
	@Modifying
	@Transactional
	@Query("UPDATE Place p SET "
			+ "p.lastDoneTask = ?#{[0].lastDoneTask}, "
			+ "p.food = ?#{[0].food}, p.wood = ?#{[0].wood}, "
			+ "p.stone = ?#{[0].stone}, p.iron = ?#{[0].iron}, "
			+ "p.foodCollectors = ?#{[0].foodCollectors}, "
			+ "p.woodCollectors = ?#{[0].woodCollectors}, "
			+ "p.stoneCollectors = ?#{[0].stoneCollectors}, "
			+ "p.ironCollectors = ?#{[0].ironCollectors} WHERE p.user.id=(SELECT u.id FROM User u WHERE u.email=?#{[1]})")
	void update(GameResource resource, String email);
}
