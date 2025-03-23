package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import io.micrometer.common.lang.NonNull;

@Data
@Entity
@Table(name = "foto_pessoa")
@SequenceGenerator(name = "seq_foto_pessoa", sequenceName = "seq_foto_pessoa", allocationSize = 1, initialValue = 1)
public class FotoPessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_foto_pessoa")
    @Column(name = "fp_id")
    private int id;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "fp_data")
    @Temporal(TemporalType.DATE)
    private Date fotoData;
    
    @Column(name = "fp_bucket", length = 50)
    private String fotoBucket;

    @Column(name = "fp_hash", length = 50)
    private String fotoHash;
}