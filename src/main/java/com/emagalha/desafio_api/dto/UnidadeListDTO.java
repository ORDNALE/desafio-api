package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.Unidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeListDTO {
    private Integer id;
    private String nome;
    private String sigla;

    public static UnidadeListDTO fromEntity(Unidade unidade) {
        return new UnidadeListDTO(
            unidade.getId(),
            unidade.getNome(),
            unidade.getSigla()
        );
    }
}