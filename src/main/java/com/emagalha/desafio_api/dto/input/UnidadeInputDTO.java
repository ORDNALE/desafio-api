package com.emagalha.desafio_api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeInputDTO {

    @Schema(example = "Secretaria Municipal de Educação", description = "Nome da unidade")
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200)
    private String nome;

    @Schema(example = "SME", description = "Sigla da unidade")
    @NotBlank(message = "Sigla é obrigatória")
    @Size(max = 20)
    private String sigla;
}