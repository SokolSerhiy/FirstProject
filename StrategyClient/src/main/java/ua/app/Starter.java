package ua.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ua.app.engine.Engine;
import ua.app.engine.Resource;

@Service
public class Starter {
	
	@Autowired
	private Resource resource;
	
	@Autowired
	private Engine engine;
	
	@Scheduled(fixedDelay=5000)
	public void work(){
		engine.work();
		resource.save();
	}
}
