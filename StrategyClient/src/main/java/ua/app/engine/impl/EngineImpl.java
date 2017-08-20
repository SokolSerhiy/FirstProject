package ua.app.engine.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.app.engine.Engine;
import ua.app.engine.ResourceEngine;

@Service
public class EngineImpl implements Engine {
	
	@Autowired
	private ResourceEngine resourceEngine;

	@PostConstruct
	public void init() {
		System.out.println("Start initialization engine");
		System.out.println("Start initialization resource engine");
		resourceEngine.init();
		System.out.println("End initialization resource engine");
		System.out.println("End initialization engine");
	}

	@Override
	public void work() {
		resourceEngine.collectResource();
	}
}