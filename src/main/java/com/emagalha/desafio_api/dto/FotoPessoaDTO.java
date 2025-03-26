package com.emagalha.desafio_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FotoPessoaDTO {
    private Integer id;
    private LocalDate fotoData;
    private String fotoBucket;
    private String fotoHash;
}