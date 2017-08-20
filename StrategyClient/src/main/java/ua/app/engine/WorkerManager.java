package ua.app.engine;

import java.util.Collection;

import ua.wrapper.Collector;
import ua.wrapper.ResourceName;

public interface WorkerManager {

	void addCollectors(int count, ResourceName name, Collection<? super Collector> collectors);

	Collector createCollector(ResourceName name);
}
