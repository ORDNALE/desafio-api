package com.emagalha.desafio_api.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("https://play.min.io") // Altere para o seu endpoint
                .credentials("minioadmin", "minioadmin") // Altere para suas credenciais
                .build();
    }
}