package ua.model.request;

import ua.model.resource.ResourceSupport;

public class ResourceRequest extends ResourceSupport{

	private int lastDoneTask;
	
	private int food;
	
	private int wood;
	
	private int stone;
	
	private int iron;
	
	private int foodCollectors;
	
	private int woodCollectors;
	
	private int stoneCollectors;
	
	private int ironCollectors;
	
	public ResourceRequest(int lastDoneTask, int food, int wood, int stone, int iron, int foodCollectors,
			int woodCollectors, int stoneCollectors, int ironCollectors) {
		this.lastDoneTask = lastDoneTask;
		this.food = food;
		this.wood = wood;
		this.stone = stone;
		this.iron = iron;
		this.foodCollectors = foodCollectors;
		this.woodCollectors = woodCollectors;
		this.stoneCollectors = stoneCollectors;
		this.ironCollectors = ironCollectors;
	}
	
	public ResourceRequest() {
	}

	public int getLastDoneTask() {
		return lastDoneTask;
	}

	public void setLastDoneTask(int lastDoneTask) {
		this.lastDoneTask = lastDoneTask;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getStone() {
		return stone;
	}

	public void setStone(int stone) {
		this.stone = stone;
	}

	public int getIron() {
		return iron;
	}

	public void setIron(int iron) {
		this.iron = iron;
	}

	public int getFoodCollectors() {
		return foodCollectors;
	}

	public void setFoodCollectors(int foodCollectors) {
		this.foodCollectors = foodCollectors;
	}

	public int getWoodCollectors() {
		return woodCollectors;
	}

	public void setWoodCollectors(int woodCollectors) {
		this.woodCollectors = woodCollectors;
	}

	public int getStoneCollectors() {
		return stoneCollectors;
	}

	public void setStoneCollectors(int stoneCollectors) {
		this.stoneCollectors = stoneCollectors;
	}

	public int getIronCollectors() {
		return ironCollectors;
	}

	public void setIronCollectors(int ironCollectors) {
		this.ironCollectors = ironCollectors;
	}
}