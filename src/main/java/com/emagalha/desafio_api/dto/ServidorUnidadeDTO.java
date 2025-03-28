package com.emagalha.desafio_api.dto;

public record ServidorUnidadeDTO(
    String nome,
    Integer idade,
    String unidadeLotacao,
    String fotoUrl
) {}