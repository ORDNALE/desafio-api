package com.emagalha.desafio_api.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ServidorTemporarioDTO {
    private Integer pessoaId;
    private Date dataAdmissao;
    private Date dataDemissao;
}