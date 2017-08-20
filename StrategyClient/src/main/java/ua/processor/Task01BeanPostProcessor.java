package ua.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import ua.annotation.task.Task01;
import ua.app.engine.Resource;
import ua.validator.Validator;
import ua.validator.exception.ПомилкаГри;
import ua.validator.task01.ClassNameValidator;
import ua.validator.task01.FieldNameValidator;

@Component
public class Task01BeanPostProcessor implements BeanPostProcessor{

	private final List<Validator> validators = new ArrayList<>();
	
	private final List<String> messages = new ArrayList<>();
	
	private final Resource resource;
	
	private final int taskNumber = 1;
	
	private boolean isStarted = false;
	
	@Autowired
	public Task01BeanPostProcessor(Resource resource) {
		this.resource = resource;
		addValidators();
	}

	@Override
	public Object postProcessAfterInitialization(Object obj, String arg1) throws BeansException {
		return obj;
	}

	@Override
	public Object postProcessBeforeInitialization(Object obj, String arg1) throws BeansException {
		if(!isStarted){
			System.out.println("Start task 1 validation");
			isStarted = true;
		}
		if(obj.getClass().isAnnotationPresent(Task01.class)){
			final String err = validate(obj);
			if(!err.isEmpty()) throw new ПомилкаГри(err);
			resource.setLastDoneTask(taskNumber);
			System.out.println("End task 1 validation");
		}
		return obj;
	}
	
	private String validate(Object obj){
		validators.forEach(v->v.validate(obj, messages));
		return messages.stream().reduce("", (acc, mess)->acc+"\n"+mess);
	}
	
	private void addValidators(){
		validators.add(new ClassNameValidator("Human"));
		validators.add(new FieldNameValidator("bellyful"));
		
	}
}