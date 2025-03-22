package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "servidor_temporario")
public class ServidorTemporario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pes_id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @Column(name = "st_data_admissao")
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;

    @Column(name = "st_data_demissao")
    @Temporal(TemporalType.DATE)
    private Date dataDemissao;
}