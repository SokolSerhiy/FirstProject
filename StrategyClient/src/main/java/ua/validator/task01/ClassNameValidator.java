package ua.validator.task01;

import java.util.List;

import ua.validator.Validator;

public class ClassNameValidator implements Validator{

	private final String className;
	
	public ClassNameValidator(String className) {
		this.className = className;
	}

	@Override
	public void validate(Object target, List<String> messages) {
		if(!target.getClass().getSimpleName().equals(className)){
			messages.add("Клас має називатись "+className);
		}
	}
}
