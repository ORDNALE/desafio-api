package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "servidor_efetivo")
public class ServidorEfetivo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pes_id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @Column(name = "se_matricula", length = 20)
    private String matricula;
}