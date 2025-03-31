package com.emagalha.desafio_api.dto.input;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaInputDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200)
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotBlank(message = "Sexo é obrigatório")
    @Size(max = 9)
    private String sexo;

    @Size(max = 200)
    private String mae;

    @Size(max = 200)
    private String pai;
}