package ua.lviv.lgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ua.lviv.lgs.repository")
public class LogosApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogosApplication.class, args);
	}

}