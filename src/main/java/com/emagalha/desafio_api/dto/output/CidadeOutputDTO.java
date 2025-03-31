package com.emagalha.desafio_api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeOutputDTO {
    private Integer id;
    private String nome;
    private String uf;
}