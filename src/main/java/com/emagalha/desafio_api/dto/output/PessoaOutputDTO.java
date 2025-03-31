package com.emagalha.desafio_api.dto.output;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaOutputDTO {


    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String sexo;
    private String mae;
    private String pai;
}