package com.sparta.airbnb_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AirbnbCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirbnbCloneApplication.class, args);
	}

}
