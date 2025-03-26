package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unidade")
@SequenceGenerator(name = "seq_unidade", sequenceName = "seq_unidade", allocationSize = 1)
public class Unidade implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_unidade")
    @Column(name = "unid_id")
    private Integer id;
    
    @Column(name = "unid_nome", length = 200, nullable = false)
    private String nome;
    
    @Column(name = "unid_sigla", length = 20)
    private String sigla;
    
    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lotacao> lotacoes = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "unidade_endereco",
        joinColumns = @JoinColumn(name = "unid_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id"))
    private Set<Endereco> enderecos = new HashSet<>();


    @Override
    public String toString() {
        return "Unidade{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Unidade other = (Unidade) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}