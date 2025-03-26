package com.emagalha.desafio_api.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções quando uma entidade não é encontrada no banco de dados.
     * Retorna status HTTP 404 (NOT_FOUND) com detalhes do erro.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            "NOT_FOUND",
            ex.getMessage(), 
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata exceções de regras de negócio violadas.
     * Retorna status HTTP 400 (BAD_REQUEST) com a mensagem da exceção.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse error = new ErrorResponse(
            "BUSINESS_ERROR",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata erros de validação dos DTOs (quando anotações como @NotBlank falham).
     * Coleta todos os erros de validação e retorna em uma mensagem concatenada.
     * Retorna status HTTP 400 (BAD_REQUEST).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
            "VALIDATION_ERROR",
            String.join("; ", errors), // Junta todos os erros de validação
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Captura qualquer exceção não tratada pelos handlers específicos acima.
     * Retorna status HTTP 500 (INTERNAL_SERVER_ERROR) com mensagem genérica.
     * Importante para não expor detalhes internos da aplicação.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "Ocorreu um erro inesperado", // Mensagem genérica
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "JSON inválido")
    public ErrorResponse handleJsonParseError(HttpMessageConversionException ex) {
        return new ErrorResponse(
            "JSON_PARSE_ERROR",
            "Erro ao processar JSON: " + ex.getMessage(),
            LocalDateTime.now()
        );
    }
}