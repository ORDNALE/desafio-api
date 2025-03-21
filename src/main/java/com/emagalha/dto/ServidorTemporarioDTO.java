package com.emagalha.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ServidorTemporarioDTO {
    private int pessoaId;
    private Date dataAdmissao;
    private Date dataDemissao;
}