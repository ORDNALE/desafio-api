package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import io.micrometer.common.lang.NonNull;

@Data
@Entity
@Table(name = "lotacao")
@SequenceGenerator(name = "seq_lotacao", sequenceName = "seq_lotacao", allocationSize = 1, initialValue = 1)
public class Lotacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lotacao")
    @Column(name = "lot_id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unid_id", nullable = false)
    private Unidade unidade;

    @Column(name = "lot_data_lotacao")
    @Temporal(TemporalType.DATE)
    private Date dataLotacao;

    @Column(name = "lot_data_remocao")
    @Temporal(TemporalType.DATE)
    private Date dataRemocao;

    @Column(name = "lot_portaria", length = 100)
    private String portaria;

    @ManyToOne
    @JoinColumn(name = "pes_id", nullable = false, insertable = false, updatable = false)
    private ServidorEfetivo servidor;

}