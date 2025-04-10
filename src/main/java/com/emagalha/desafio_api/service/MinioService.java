package com.emagalha.desafio_api.service;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    private static final String BUCKET_NAME = "minhas-imagens";

    public void createBucketIfNotExists() {
        try {
            boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                    .bucket(BUCKET_NAME)
                    .build());
            
            if (!bucketExists) {
                minioClient.makeBucket(
                    MakeBucketArgs.builder()
                        .bucket(BUCKET_NAME)
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao verificar/criar bucket no MinIO", e);
        }
    }

    public String uploadFoto(InputStream fileStream, String objectName, String contentType) {
        createBucketIfNotExists();
        
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .stream(fileStream, -1, 10485760) // 10 MB
                    .contentType(contentType)
                    .build());
            
            return gerarUrlTemporaria(objectName, 5);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao fazer upload da foto para o MinIO", e);
        }
    }

    public String gerarUrlTemporaria(String objectName, int minutosExpiracao) {
        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .expiry(minutosExpiracao, TimeUnit.MINUTES)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Falha ao gerar URL temporária", e);
        }
    }

    public boolean objectExists(String objectName) {
        try {
            minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> listarObjetos() {
        List<String> objetos = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                    .bucket(BUCKET_NAME)
                    .recursive(true)
                    .build());
            
            for (Result<Item> result : results) {
                Item item = result.get();
                objetos.add(item.objectName());
            }
            return objetos;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao listar objetos no MinIO", e);
        }
    }
}