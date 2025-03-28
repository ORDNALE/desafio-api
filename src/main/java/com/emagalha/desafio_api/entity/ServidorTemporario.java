package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servidor_temporario")
public class ServidorTemporario implements Serializable {
    
    @Id
    @Column(name = "pes_id")
    private Integer id;
    
    @MapsId
    @OneToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;
    
    @Column(name = "st_data_admissao")
    
    private LocalDate dataAdmissao;
    
    @Column(name = "st_data_demissao")
    
    private LocalDate dataDemissao;

    @Override
    public String toString() {
        return "ServidorTemporario{" +
            "id=" + id +
            ", dataAdmissao=" + dataAdmissao +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServidorTemporario that = (ServidorTemporario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}