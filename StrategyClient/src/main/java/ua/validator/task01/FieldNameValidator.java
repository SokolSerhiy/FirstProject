package ua.validator.task01;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.util.ReflectionUtils;

import ua.validator.Validator;

public class FieldNameValidator implements Validator {

	private final String fieldName;
	
	public FieldNameValidator(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public void validate(Object target, List<String> messages) {
		Field field = ReflectionUtils.findField(target.getClass(), fieldName);
		if(field==null){
			messages.add("У класі має бути поле з назвою "+fieldName);
		}
	}
}