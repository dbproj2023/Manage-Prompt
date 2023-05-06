package com.dbproj.manageprompt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ManagepromptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagepromptApplication.class, args);
	}

}
