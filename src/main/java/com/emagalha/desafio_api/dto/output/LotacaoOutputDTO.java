package com.emagalha.desafio_api.dto.output;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoOutputDTO {

    @Schema(example = "1", description = "ID da lotação")
    private Integer id;

    @Schema(description = "Dados da pessoa lotada")
    private PessoaOutputDTO pessoa;

    @Schema(description = "Dados da unidade")
    private UnidadeOutputDTO unidade;

    @Schema(example = "2023-01-01", description = "Data de lotação")
    private LocalDate dataLotacao;

    @Schema(example = "2023-12-31", description = "Data de remoção")
    private LocalDate dataRemocao;

    @Schema(example = "Portaria 123/2023", description = "Número da portaria")
    private String portaria;
}