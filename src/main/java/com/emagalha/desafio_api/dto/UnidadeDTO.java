package com.emagalha.desafio_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeDTO {
    private Integer id;

    @NotBlank
    @Size(max = 200)
    private String nome;

    @Size(max = 20)
    private String sigla;
}