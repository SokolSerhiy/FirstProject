package ua.app.engine;

import ua.wrapper.ResourceName;

public interface Resource {

	void save();
	
	void load();
	
	int getLastDoneTask();

	void setLastDoneTask(int lastDoneTask);

	int getFoodCollectors();

	int getWoodCollectors();

	int getStoneCollectors();

	int getIronCollectors();

	void incrementCollectors(ResourceName name);
	
	void decrementCollectors(ResourceName name);
	
	void collect(ResourceName name, int count);
	
	int spend(ResourceName name, int count);
}
