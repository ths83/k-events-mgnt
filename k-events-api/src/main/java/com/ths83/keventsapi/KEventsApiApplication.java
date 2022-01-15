package com.ths83.keventsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.ths83.keventsapi.repositories")
public class KEventsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KEventsApiApplication.class, args);
	}

}
