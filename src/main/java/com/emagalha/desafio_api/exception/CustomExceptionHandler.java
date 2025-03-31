// package com.emagalha.desafio_api.exception;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice
// public class CustomExceptionHandler {

//     @ExceptionHandler(AuthenticationException.class)
//     public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
//     }
// }