package com.emagalha.desafio_api.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporarioOutputDTO {
    @Schema(example = "1", description = "ID do servidor temporário")
    private Integer id;
    
    @Schema(example = "2023-01-01", description = "Data de admissão")
    private LocalDate dataAdmissao;
    
    @Schema(example = "2023-12-31", description = "Data de demissão")
    private LocalDate dataDemissao;
    
    @Schema(description = "Dados da pessoa associada")
    private Integer pessoa;
}