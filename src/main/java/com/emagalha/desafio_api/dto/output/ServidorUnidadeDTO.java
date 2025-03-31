package com.emagalha.desafio_api.dto.output;

public interface ServidorUnidadeDTO {
    String getNome();
    Integer getIdade();
    String getUnidadeLotacao();
    String getFotografia();
    
    // Método default para URL completa da foto (se necessário)
    default String getUrlFoto() {
        return !getFotografia().isEmpty() ? 
            "/api/fotos/" + getFotografia() : 
            "/images/default-profile.jpg";
    }
}