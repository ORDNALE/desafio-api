package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.Unidade;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoDTO {
    private Integer id;
    @NotNull
    private Integer pessoaId;
    @NotNull
    private Integer unidadeId;
    @NotNull
    private LocalDate dataLotacao;
    private LocalDate dataRemocao;
    @Size(max = 100)
    private String portaria;
}