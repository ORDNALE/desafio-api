package com.emagalha.desafio_api.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    private int id;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;
    private int cidadeId;
}