package com.emagalha.desafio_api.dto;

import java.time.LocalDate;

import com.emagalha.desafio_api.entity.Lotacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoListDTO {
    private Integer id;
    private Integer pessoaId;
    private Integer unidadeId;
    private LocalDate dataLotacao;
    private String portaria;

    public static LotacaoListDTO fromEntity(Lotacao lotacao) {
        return new LotacaoListDTO(
            lotacao.getId(),
            lotacao.getPessoa().getId(),
            lotacao.getUnidade().getId(),
            lotacao.getDataLotacao(),
            lotacao.getPortaria()
        );
    }
}