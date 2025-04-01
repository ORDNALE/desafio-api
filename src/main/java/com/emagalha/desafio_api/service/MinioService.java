package com.emagalha.desafio_api.service;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    private final String bucketName = "minhas-imagens";

    public void createBucketIfNotExists() throws Exception {
        try {
            boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
            
            if (!bucketExists) {
                minioClient.makeBucket(
                    MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            
            }
        } catch (Exception e) {
            throw new Exception("Falha ao verificar/criar bucket: " + e.getMessage(), e);
        }
    }

    public String uploadFoto(InputStream file, String objectName, String contentType) throws Exception {
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(file, -1, 10485760)
                .contentType(contentType)
                .build());
        
        return gerarUrlTemporaria(bucketName, objectName);
    }


    public String gerarUrlTemporaria(String bucketName, String objectName) throws Exception {
        try {
            
            int expiracao = (int) TimeUnit.MINUTES.toSeconds(5);

            // Gera a URL pré-assinada
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(expiracao)
                    .build()
            );
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            throw new Exception("Erro ao gerar URL temporária: " + e.getMessage(), e);
        }
    }

}