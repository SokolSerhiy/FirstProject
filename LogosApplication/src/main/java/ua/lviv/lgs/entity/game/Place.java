package ua.lviv.lgs.entity.game;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ua.lviv.lgs.entity.User;

@Entity
@Table(name="place")
public class Place {

	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;
	
	private int lastDoneTask;
	
	private int food;
	
	private int wood;
	
	private int stone;
	
	private int iron;
	
	private int foodCollectors;
	
	private int woodCollectors;
	
	private int stoneCollectors;
	
	private int ironCollectors;
	
	@OneToOne(fetch=FetchType.LAZY)
	private User user;
	
	public Place(User user) {
		this.user = user;
	}
	
	public Place() {
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

	public int getLastDoneTask() {
		return lastDoneTask;
	}

	public void setLastDoneTask(int lastDoneTask) {
		this.lastDoneTask = lastDoneTask;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
