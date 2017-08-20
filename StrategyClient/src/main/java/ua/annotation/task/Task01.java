package ua.annotation.task;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import ua.annotation.qualifier.Task01Qualifier;

@Retention(RUNTIME)
@Target(TYPE)
@Component
@Task01Qualifier
public @interface Task01 {

}
