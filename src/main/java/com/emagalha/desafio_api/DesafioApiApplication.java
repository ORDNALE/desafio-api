package com.emagalha.desafio_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.emagalha.entity")
public class DesafioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApiApplication.class, args);
	}

}
