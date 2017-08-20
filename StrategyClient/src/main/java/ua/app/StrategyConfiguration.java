package ua.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.annotation.qualifier.ObjectMapperQualifier;

@Configuration
public class StrategyConfiguration {

	@Bean
	@ObjectMapperQualifier
	public ObjectMapper getMapper(){
		return new ObjectMapper();
	}
}
