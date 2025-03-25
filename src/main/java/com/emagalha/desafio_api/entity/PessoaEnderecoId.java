package com.emagalha.desafio_api.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class PessoaEnderecoId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pessoa;
    private Integer endereco;
}