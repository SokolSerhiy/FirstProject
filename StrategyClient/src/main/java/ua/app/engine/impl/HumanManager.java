package ua.app.engine.impl;

import static ua.util.Reflection.getInstance;

import java.util.Collection;

import ua.app.engine.WorkerManager;
import ua.model.resource.Bellyful;
import ua.wrapper.Collector;
import ua.wrapper.HumanWrapper;
import ua.wrapper.ResourceName;

public class HumanManager implements WorkerManager{

	private final Bellyful bellyful = Bellyful.restore();
	
	private int j = 0;
	
	private final Object task1;

	public HumanManager(Object task1) {
		this.task1 = task1;
	}

	@Override
	public void addCollectors(int count, ResourceName name, Collection<? super Collector> collectors) {
		for (int i = 0; i < count; i++, j++) {
			collectors.add(createCollector(name));
		}
	}

	@Override
	public Collector createCollector(ResourceName name) {
		HumanWrapper wrapper = new HumanWrapper(getInstance(task1.getClass()), name);
		int food = j<bellyful.getHumanBellyfuls().size() ? bellyful.getHumanBellyfuls().get(j) : 0;
		wrapper.setBellyful(food);
		return wrapper;
	}


}
