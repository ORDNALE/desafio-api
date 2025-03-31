package com.emagalha.desafio_api.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeOutputDTO {

    @Schema(example = "1", description = "ID da unidade")
    private Integer id;

    @Schema(example = "Secretaria Municipal de Educação", description = "Nome da unidade")
    private String nome;

    @Schema(example = "SME", description = "Sigla da unidade")
    private String sigla;
}