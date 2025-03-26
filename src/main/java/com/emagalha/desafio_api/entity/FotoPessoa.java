package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "foto_pessoa")
@SequenceGenerator(name = "seq_foto_pessoa", sequenceName = "seq_foto_pessoa", allocationSize = 1)
public class FotoPessoa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_foto_pessoa")
    @Column(name = "fp_id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;
    
    @Column(name = "fp_data")
    private LocalDate data;
    
    @Column(name = "fp_bucket", length = 50)
    private String bucket;
    
    @Column(name = "fp_hash", length = 50)
    private String hash;

    @Override
    public String toString() {
        return "FotoPessoa{" +
            "id=" + id +
            ", bucket='" + bucket + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FotoPessoa that = (FotoPessoa) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}