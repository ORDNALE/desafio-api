package com.emagalha.desafio_api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoInputDTO {

    @Schema(example = "1", description = "ID da pessoa associada ao servidor")
    @NotNull(message = "ID da pessoa é obrigatório")
    private Integer pessoaId;

    @Schema(example = "123456", description = "Matrícula do servidor")
    @NotBlank(message = "Matrícula é obrigatória")
    @Size(max = 20)
    private String matricula;
}