package com.emagalha.desafio_api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PessoaListDTO {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
}