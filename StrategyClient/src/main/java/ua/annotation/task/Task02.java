package ua.annotation.task;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import ua.annotation.qualifier.Task02Qualifier;

@Retention(RUNTIME)
@Target(TYPE)
@Component
@Task02Qualifier
public @interface Task02 {

}
