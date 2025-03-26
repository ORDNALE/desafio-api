package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@Table(name = "lotacao")
@SequenceGenerator(name = "seq_lotacao", sequenceName = "seq_lotacao", allocationSize = 1)
public class Lotacao implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lotacao")
    @Column(name = "lot_id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unid_id", nullable = false)
    private Unidade unidade;
    
    @Column(name = "lot_data_lotacao")
    private LocalDate dataLotacao;
    
    @Column(name = "lot_data_remocao")
    private LocalDate dataRemocao;
    
    @Column(name = "lot_portaria", length = 100)
    private String portaria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", insertable = false, updatable = false)
    private ServidorEfetivo servidorEfetivo;

    @Override
    public String toString() {
        return "Lotacao{" +
            "id=" + id +
            ", dataLotacao=" + dataLotacao +
            '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lotacao lotacao = (Lotacao) o;
        return id != null && Objects.equals(id, lotacao.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}