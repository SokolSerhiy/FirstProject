package ua.ztest;

import ua.annotation.task.Task02;

@Task02
public class FoodCollector extends Human{

	Tool tool = new Tool();
	
	public int advancedCollectFood(){
		System.out.println("Food collector");
		return tool.increase(collectFood());
	}
}
