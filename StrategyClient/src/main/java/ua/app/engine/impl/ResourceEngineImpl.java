package ua.app.engine.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.annotation.qualifier.Task01Qualifier;
import ua.annotation.qualifier.Task02Qualifier;
import ua.app.engine.Resource;
import ua.app.engine.ResourceEngine;
import ua.app.engine.WorkerManager;
import ua.model.resource.Bellyful;
import ua.wrapper.Collector;
import ua.wrapper.ResourceName;

@Service
public class ResourceEngineImpl implements ResourceEngine {

	@Autowired(required = false)
	@Task01Qualifier
	private Object task1;
	
	@Autowired(required = false)
	@Task02Qualifier
	private List<Object> task2;

	@Autowired
	private Resource resource;
	
	private WorkerManager manager;

	private final List<Collector> collectors = new ArrayList<>();

	private static final int TAKE_FOOD_PER_ONE_TIME = 50;

	public void init() {
		manager = selectManager();
		manager.addCollectors(resource.getFoodCollectors(), ResourceName.FOOD, collectors);
		manager.addCollectors(resource.getWoodCollectors(), ResourceName.WOOD, collectors);
		manager.addCollectors(resource.getStoneCollectors(), ResourceName.STONE, collectors);
		manager.addCollectors(resource.getIronCollectors(), ResourceName.IRON, collectors);
		int size = collectors.size();
		for (int i = 0; i < resource.getLastDoneTask()*5-size; i++) {
			collectors.add(manager.createCollector(ResourceName.values()[i % 4]));
			resource.incrementCollectors(ResourceName.values()[i % 4]);
		}
	}
	
	private WorkerManager selectManager() {
		if(task2!=null&&!task2.isEmpty()) {
			return new CollectorManager(task2);
		}
		return new HumanManager(task1);
	}
	
	public void collectResource() {
		Bellyful bellyful = Bellyful.restore();
		bellyful.clearHumanBellyfuls();
		for (int i = 0; i < resource.getLastDoneTask()*5; i++) {
			Collector collector = collectors.get(i);
			if (collector.isHungry()) {
				takeFood(collector, i);
			}
			bellyful.getHumanBellyfuls().add(collector.getBellyful());
			resource.collect(collector.getCollectedResourceName(), collector.collect());
		}
		bellyful.save();
		System.out.println(resource);
	}

	private void takeFood(Collector collector, int i) {
		int allFood = resource.spend(ResourceName.FOOD, TAKE_FOOD_PER_ONE_TIME);
		collector.takeFood(allFood);
		if(allFood == 0) changeSpecialization(collector, i);
	}

	private void changeSpecialization(Collector collector, int i) {
		if (collector.getCollectedResourceName() != ResourceName.FOOD) {
			collectors.set(i, manager.createCollector(ResourceName.FOOD));
			resource.decrementCollectors(collector.getCollectedResourceName());
			resource.incrementCollectors(ResourceName.FOOD);
		}
	}
}
