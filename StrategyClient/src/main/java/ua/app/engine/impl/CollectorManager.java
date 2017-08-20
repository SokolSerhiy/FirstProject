package ua.app.engine.impl;

import static ua.util.Reflection.getInstance;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.app.engine.WorkerManager;
import ua.model.resource.Bellyful;
import ua.wrapper.Collector;
import ua.wrapper.CollectorWrapper;
import ua.wrapper.ResourceName;

public class CollectorManager implements WorkerManager{

	private final Bellyful bellyful = Bellyful.restore();
	
	private int j = 0;
	
	private final List<Object> task2;
	
	private Map<ResourceName, Object> map = new HashMap<>();
	
	public CollectorManager(List<Object> task2) {
		this.task2 = task2;
		addToMap();
	}

	@Override
	public void addCollectors(int count, ResourceName name, Collection<? super Collector> collectors) {
		for (int i = 0; i < count; i++, j++) {
			collectors.add(createCollector(name));
		}
	}

	@Override
	public Collector createCollector(ResourceName name) {
		CollectorWrapper wrapper = new CollectorWrapper(getInstance(map.get(name).getClass()), name);
		int food = j<bellyful.getHumanBellyfuls().size() ? bellyful.getHumanBellyfuls().get(j) : 0;
		wrapper.setBellyful(food);
		return wrapper;
	}
	
	private void addToMap() {
		for (Object collector : task2) {
			if(collector.getClass().getSimpleName().startsWith("Food")) {
				map.put(ResourceName.FOOD, collector);
			}else if(collector.getClass().getSimpleName().startsWith("Wood")) {
				map.put(ResourceName.WOOD, collector);
			}else if(collector.getClass().getSimpleName().startsWith("Iron")) {
				map.put(ResourceName.IRON, collector);
			}else if(collector.getClass().getSimpleName().startsWith("Stone")) {
				map.put(ResourceName.STONE, collector);
			}
		}
	}

	
	
}
