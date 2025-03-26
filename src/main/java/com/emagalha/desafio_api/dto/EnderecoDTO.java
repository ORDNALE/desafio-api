package com.emagalha.desafio_api.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Integer id;
    private String tipoLogradouro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private Integer cidadeId;
    private Integer unidadeId; 
}