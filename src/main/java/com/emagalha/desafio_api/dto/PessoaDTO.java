package com.emagalha.desafio_api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PessoaDTO {
    private Integer id;
    private String nome;
    private Date dataNascimento;
    private String sexo;
    private String mae;
    private String pai;
}