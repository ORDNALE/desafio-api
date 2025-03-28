package com.emagalha.desafio_api.dto;

import lombok.Data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class FotoPessoaDTO {
    @Schema(example = "1", description = "ID FotoPessoa")
    private Integer id;
    
    private LocalDate fotoData;
    private String fotoBucket;
    private String fotoHash;
}