package com.emagalha.desafio_api.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Integer id;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;
    private Integer cidadeId;
    private Integer unidadeId; 
}