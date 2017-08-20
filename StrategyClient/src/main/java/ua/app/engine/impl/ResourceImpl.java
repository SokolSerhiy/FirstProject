package ua.app.engine.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.annotation.qualifier.AuthHttpQualifier;
import ua.app.engine.Resource;
import ua.http.Http;
import ua.model.request.ResourceRequest;
import ua.smart.SmartUrl;
import ua.wrapper.ResourceName;

@Service
public class ResourceImpl implements Resource {

	private final Http http;
	
	private final SmartUrl smartUrl;
	
	private int lastDoneTask;
	
	private int food;
	
	private int wood;
	
	private int stone;
	
	private int iron;
	
	private int foodCollectors;
	
	private int woodCollectors;
	
	private int stoneCollectors;
	
	private int ironCollectors;
	
	@Autowired
	public ResourceImpl(@AuthHttpQualifier Http http, SmartUrl smartUrl) {
		this.http = http;
		this.smartUrl = smartUrl;
	}

	@Override
	public void save() {
		ResourceRequest request = new ResourceRequest(lastDoneTask, food, wood, stone, iron, foodCollectors, woodCollectors, stoneCollectors, ironCollectors);
		http.post(smartUrl.getGame(), request).close();
	}

	@Override
	public void load() {
		ResourceRequest json = http.get(smartUrl.getGame()).json(ResourceRequest.class);
		setFood(json.getFood());
		setFoodCollectors(json.getFoodCollectors());
		setIron(json.getIron());
		setIronCollectors(json.getIronCollectors());
		setLastDoneTask(json.getLastDoneTask());
		setStone(json.getStone());
		setStoneCollectors(json.getStoneCollectors());
		setWood(json.getWood());
		setWoodCollectors(json.getWoodCollectors());
	}
	
	@PostConstruct
	public void init(){
		System.out.println("Start loading resource from server");
		load();
		System.out.println("End loading resource from server");
	}

	public int getLastDoneTask() {
		return lastDoneTask;
	}

	public void setLastDoneTask(int lastDoneTask) {
		if(this.lastDoneTask<lastDoneTask)
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

	@Override
	public String toString() {
		return "ResourceImpl [lastDoneTask=" + lastDoneTask + ", food=" + food + ", wood=" + wood + ", stone=" + stone
				+ ", iron=" + iron + ", foodCollectors=" + foodCollectors + ", woodCollectors=" + woodCollectors
				+ ", stoneCollectors=" + stoneCollectors + ", ironCollectors=" + ironCollectors + "]";
	}

	@Override
	public void incrementCollectors(ResourceName name) {
		switch (name) {
		case FOOD:
			foodCollectors++;
			System.out.println("Food collectors +1");
			break;
		case WOOD:
			woodCollectors++;
			System.out.println("Wood collectors +1");
			break;
		case IRON:
			ironCollectors++;
			System.out.println("Iron collectors +1");
			break;
		case STONE:
			stoneCollectors++;
			System.out.println("Stone collectors +1");
			break;
		}
	}

	@Override
	public void decrementCollectors(ResourceName name) {
		switch (name) {
		case FOOD:
			foodCollectors--;
			System.out.println("Food collectors -1");
			break;
		case WOOD:
			woodCollectors--;
			System.out.println("Wood collectors -1");
			break;
		case IRON:
			ironCollectors--;
			System.out.println("Iron collectors -1");
			break;
		case STONE:
			stoneCollectors--;
			System.out.println("Stone collectors -1");
			break;
		}
	}

	@Override
	public void collect(ResourceName name, int count) {
		switch (name) {
		case FOOD:
			food+=count;
			break;
		case WOOD:
			wood+=count;
			break;
		case IRON:
			iron+=count;
			break;
		case STONE:
			stone+=count;
			break;
		}
	}

	@Override
	public int spend(ResourceName name, int count) {
		switch (name) {
		case FOOD:
			if(count>=food) {
				count = food;
				food = 0;
			} else {
				food -= count;
			}
			return count;
		case WOOD:
			if(count>=wood) {
				count = wood;
				wood = 0;
			} else {
				wood -= count;
			}
			return count;
		case IRON:
			if(count>=iron) {
				count = iron;
				iron = 0;
			} else {
				iron -= count;
			}
			return count;
		case STONE:
			if(count>=stone) {
				count = stone;
				stone = 0;
			} else {
				stone -= count;
			}
			return count;
		default : throw new IllegalArgumentException("Не може бути");
		}
	}
}
