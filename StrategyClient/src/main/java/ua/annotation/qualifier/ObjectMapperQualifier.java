package ua.annotation.qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import org.springframework.beans.factory.annotation.Qualifier;
@Qualifier
@Retention(RUNTIME)
public @interface ObjectMapperQualifier {

}
