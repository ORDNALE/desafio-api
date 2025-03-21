package com.emagalha.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FotoPessoaDTO {
    private int id;
    private int pessoaId;
    private Date fotoData;
    private String fotoBucket;
    private String fotoHash;
}