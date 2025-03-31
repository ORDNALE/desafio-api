package com.emagalha.desafio_api.dto.input;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoInputDTO {

    @Schema(example = "1", description = "ID da pessoa a ser lotada")
    @NotNull(message = "ID da pessoa é obrigatório")
    private Integer pessoaId;

    @Schema(example = "1", description = "ID da unidade de lotação")
    @NotNull(message = "ID da unidade é obrigatório")
    private Integer unidadeId;

    @Schema(example = "2023-01-01", description = "Data de lotação")
    @NotNull(message = "Data de lotação é obrigatória")
    private LocalDate dataLotacao;

    @Schema(example = "2023-12-31", description = "Data de remoção (opcional)")
    private LocalDate dataRemocao;

    @Schema(example = "Portaria 123/2023", description = "Número da portaria")
    @Size(max = 100)
    private String portaria;
}