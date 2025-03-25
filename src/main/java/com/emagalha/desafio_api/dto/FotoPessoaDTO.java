package com.emagalha.desafio_api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FotoPessoaDTO {
    private Integer id;
    private Integer pessoaId;
    private Date fotoData;
    private String fotoBucket;
    private String fotoHash;
}