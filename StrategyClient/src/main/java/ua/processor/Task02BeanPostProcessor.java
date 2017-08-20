package ua.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import ua.annotation.qualifier.Task02Qualifier;
import ua.annotation.task.Task02;
import ua.app.engine.Resource;
import ua.validator.Validator;
import ua.validator.exception.ПомилкаГри;
import ua.validator.task01.FieldNameValidator;

@Component
public class Task02BeanPostProcessor {
	
	private final List<Object> collectors;

	private final List<Validator> validators = new ArrayList<>();
	
	private final List<String> messages = new ArrayList<>();
	
	private final Resource resource;
	
	private final int taskNumber = 2;
	
	private String err = "";
	
	@Autowired(required=false)
	public Task02BeanPostProcessor(Resource resource, @Task02Qualifier List<Object> collectors) {
		this.resource = resource;
		this.collectors = collectors;
		addValidators();
	}
	
	@PostConstruct
	public void process() {
		if(collectors!=null&&!collectors.isEmpty()) {
			System.out.println("Start task 2 validation");
			if(!err.isEmpty()) throw new ПомилкаГри(err);
			if(collectors.size() < 4) throw new ПомилкаГри("Класів має бути 4, а є "+collectors.size());
			resource.setLastDoneTask(taskNumber);
			System.out.println("End task 2 validation");
		}
	}
	
	private String validate(Object obj){
		validators.forEach(v->v.validate(obj, messages));
		return messages.stream().reduce("", (acc, mess)->acc+"\n"+mess);
	}
	
	private void addValidators(){
		validators.add(new FieldNameValidator("bellyful"));
	}

}
