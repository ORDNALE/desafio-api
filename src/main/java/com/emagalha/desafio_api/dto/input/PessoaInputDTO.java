package com.emagalha.desafio_api.dto.input;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaInputDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200)
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Schema(
        description = "Data de nascimento no formato YYYY-MM-DD",
        example = "1990-01-01",
        type = "string",  
        format = "date" 
    )
    @PastOrPresent(message = "Data de nascimento não pode ser futura")
    private LocalDate dataNascimento;

    @NotBlank(message = "Sexo é obrigatório")
    @Size(max = 9)
    private String sexo;

    @Size(max = 200)
    private String mae;

    @Size(max = 200)
    private String pai;
}