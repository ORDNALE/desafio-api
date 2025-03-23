package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

import io.micrometer.common.lang.NonNull;

@Data
@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1, initialValue = 1)
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
    @Column(name = "end_id")
    private int id;

    @Column(name = "end_tipo_logradouro", length = 50)
    private String tipoLogradouro;

    @Column(name = "end_logradouro", length = 200)
    private String logradouro;

    @Column(name = "end_numero")
    private String numero;

    @Column(name = "end_bairro", length = 200)
    private String bairro;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "cid_id", nullable = false)
    private Cidade cidade;

    @ManyToMany(mappedBy = "enderecos")
    private List<Unidade> unidades;

    @ManyToMany(mappedBy = "enderecos")
    private List<Pessoa> pessoas;
}