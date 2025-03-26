package com.emagalha.desafio_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ServidorTemporarioDTO {
    private Integer pessoaId;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
}