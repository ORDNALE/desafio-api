package com.emagalha.desafio_api.dto;

import lombok.Data;

@Data
public class EnderecoFuncionalDTO {
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
}