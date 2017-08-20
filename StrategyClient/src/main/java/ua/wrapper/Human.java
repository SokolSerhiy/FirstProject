package ua.wrapper;

public interface Human {

	boolean isHungry();
	
	void takeFood(int food);
	
	void setBellyful(int food);
	
	int getBellyful();
	
}
