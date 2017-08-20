package ua.lviv.lgs.mapper;

import ua.lviv.lgs.entity.game.Place;
import ua.lviv.lgs.model.GameResource;

public interface PlaceMapper {

	public static GameResource toGameResource(Place place) {
		int food = place.getFood();
		int foodCollectors = place.getFoodCollectors();
		int iron = place.getIron();
		int ironCollectors = place.getIronCollectors();
		int lastDoneTask = place.getLastDoneTask();
		int stone = place.getStone();
		int stoneCollectors = place.getStoneCollectors();
		int wood = place.getWood();
		int woodCollectors = place.getWoodCollectors();
		return new GameResource(lastDoneTask, food, wood, stone, iron, foodCollectors, woodCollectors, stoneCollectors, ironCollectors);
	}
}
