package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

    @Data
    @Entity
    @Table(name = "pessoa_endereco")
    @IdClass(PessoaEnderecoId.class)
    public class PessoaEndereco implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @ManyToOne
        @JoinColumn(name = "pes_id", nullable = false)
        private Pessoa pessoa;

        @Id
        @ManyToOne
        @JoinColumn(name = "end_id", nullable = false)
        private Endereco endereco;
    }
