package com.emagalha.desafio_api.dto;

import lombok.Data;
import java.util.Date;

import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.Unidade;

@Data
public class LotacaoDTO {
    private Integer id;
    private Pessoa pessoa;
    private Unidade unidade;
    private Date dataLotacao;
    private Date dataRemocao;
    private String portaria;
}