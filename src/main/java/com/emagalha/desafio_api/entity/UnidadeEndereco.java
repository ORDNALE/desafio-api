package com.emagalha.desafio_api.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unidade_endereco")
@SequenceGenerator(name = "seq_unidade_endereco", sequenceName = "seq_unidade_endereco", allocationSize = 1, initialValue = 1)
public class UnidadeEndereco implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_unidade_endereco")
    @Column(name = "end_id")
    private int enderecoId;

    @Column(name = "unid_id")
    private int unidadeId;
}