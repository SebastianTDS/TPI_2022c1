package com.fantasticos.algorithmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class AlgorithmServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(AlgorithmServiceApp.class, args);
	}

}
