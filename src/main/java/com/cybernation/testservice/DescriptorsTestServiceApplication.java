package com.cybernation.testservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;

@SpringBootApplication(exclude = MongoReactiveDataAutoConfiguration.class)
public class DescriptorsTestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DescriptorsTestServiceApplication.class, args);
	}

}
