package ua.ztest;

import ua.annotation.task.Task02;

@Task02
public class WoodCollector extends Human{

	Tool tool = new Tool();
	
	public int advancedCollectWood(){
		System.out.println("Wood collector");
		return tool.increase(collectWood());
	}
}
