package com.emagalha.desafio_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorEfetivoDTO {
        private Integer pessoaId;

        @NotBlank(message = "Matrícula é obrigatória")
        @Size(max = 20)
        private String matricula;
    }