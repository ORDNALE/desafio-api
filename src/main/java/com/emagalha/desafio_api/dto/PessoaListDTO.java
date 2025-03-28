package com.emagalha.desafio_api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PessoaListDTO {
    private Integer id;
    private String nome;
    
    private LocalDate dataNascimento;
}