package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity
@Table(name = "servidor_temporario")
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "pes_id")
public class ServidorTemporario extends Pessoa {

    @Column(name = "st_data_admissao")
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;

    @Column(name = "st_data_demissao")
    @Temporal(TemporalType.DATE)
    private Date dataDemissao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", insertable = false, updatable = false)
    private Pessoa pessoa;
}