package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1, initialValue = 1)
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
    @Column(name = "end_id")
    private Integer id;

    @Column(name = "end_tipo_logradouro", length = 50, nullable = false)
    private String tipoLogradouro;

    @Column(name = "end_logradouro", length = 200, nullable = false)
    private String logradouro;

    @Column(name = "end_numero", nullable = false)
    private String numero;

    @Column(name = "end_bairro", length = 200, nullable = false)
    private String bairro;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cid_id", nullable = false)
    private Cidade cidade;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "pessoa_endereco",
        joinColumns = @JoinColumn(name = "end_id"),
        inverseJoinColumns = @JoinColumn(name = "pes_id")
    )
    private List<Pessoa> pessoas;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "unidade_endereco",
        joinColumns = @JoinColumn(name = "end_id"),
        inverseJoinColumns = @JoinColumn(name = "unid_id")
    )
    private List<Unidade> unidades;
}
