package com.emagalha.desafio_api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ServidorTemporarioInputDTO {
    @NotNull(message = "Data de admissão é obrigatória")
    @Schema(example = "2023-01-01", description = "Data de admissão no formato YYYY-MM-DD")
    private LocalDate dataAdmissao;

    @Schema(example = "2023-12-31", description = "Data de demissão no formato YYYY-MM-DD (opcional)")
    private LocalDate dataDemissao;

    @NotNull(message = "ID da pessoa é obrigatório")
    @Schema(example = "1", description = "ID da pessoa associada ao servidor")
    private Integer pessoaId;
}