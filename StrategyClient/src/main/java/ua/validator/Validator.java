package ua.validator;

import java.util.List;

public interface Validator {

	void validate(Object target, List<String> messages);
}
