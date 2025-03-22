package com.emagalha.desafio_api.dto;

import lombok.Data;
import java.util.Date;

@Data
public class LotacaoDTO {
    private int id;
    private int pessoaId;
    private int unidadeId;
    private Date dataLotacao;
    private Date dataRemocao;
    private String portaria;
}