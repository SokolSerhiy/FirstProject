package ua.ztest;

import ua.annotation.task.Task02;

@Task02
public class IronCollector extends Human{

	Tool tool = new Tool();
	
	public int advancedCollectIron(){
		System.out.println("Iron collector");
		return tool.increase(collectIron());
	}
}
