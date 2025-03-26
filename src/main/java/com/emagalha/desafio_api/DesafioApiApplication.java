package com.emagalha.desafio_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DesafioApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioApiApplication.class, args);
    }

}