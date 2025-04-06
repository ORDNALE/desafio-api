package com.emagalha.desafio_api.util.minioResource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.emagalha.desafio_api.service.MinioService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MinioInitializer implements CommandLineRunner {
    
    private final MinioService minioService;

    @Override
    public void run(String... args) throws Exception {
        minioService.createBucketIfNotExists();
    }
}