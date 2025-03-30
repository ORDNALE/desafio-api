package com.emagalha.desafio_api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MinioIntegrationException extends RuntimeException {
    public MinioIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
