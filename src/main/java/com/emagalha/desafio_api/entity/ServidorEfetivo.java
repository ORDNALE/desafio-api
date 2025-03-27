package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servidor_efetivo")
public class ServidorEfetivo implements Serializable {
    
    @Id
    @Column(name = "pes_id")
    private Integer id;
    
    @MapsId
    @OneToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;
    
    @Column(name = "se_matricula", length = 20, unique = true)
    private String matricula;

    @Override
    public String toString() {
        return "ServidorEfetivo{" +
            "id=" + id +
            ", matricula='" + matricula + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServidorEfetivo that = (ServidorEfetivo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}