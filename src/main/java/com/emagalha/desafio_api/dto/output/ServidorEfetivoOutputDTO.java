package com.emagalha.desafio_api.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoOutputDTO {

    @Schema(example = "1", description = "ID do servidor efetivo")
    private Integer id;

    @Schema(example = "5050", description = "Matrícula do servidor")
    private String matricula;

    @Schema(description = "Dados da pessoa associada")
    private PessoaOutputDTO pessoa;

}