package ua.ztest;

import ua.annotation.task.Task02;

@Task02
public class StoneCollector extends Human{

	Tool tool = new Tool();
	
	public int advancedCollectStone(){
		System.out.println("Stone collector");
		return tool.increase(collectStone());
	}
}
