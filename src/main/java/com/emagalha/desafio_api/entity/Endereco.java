package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1)
public class Endereco implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
    @Column(name = "end_id")
    private Integer id;
    
    @Column(name = "end_tipo_logradouro", length = 50)
    private String tipoLogradouro;
    
    @Column(name = "end_logradouro", length = 200, nullable = false)
    private String logradouro;
    
    @Column(name = "end_numero")
    private Integer numero;
    
    @Column(name = "end_bairro", length = 200)
    private String bairro;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_id", nullable = false)
    private Cidade cidade;
    
    @ManyToMany
    @JoinTable(
        name = "pessoa_endereco",
        joinColumns = @JoinColumn(name = "end_id"),
        inverseJoinColumns = @JoinColumn(name = "pes_id"))
    private Set<Pessoa> pessoas = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "unidade_endereco",
        joinColumns = @JoinColumn(name = "end_id"),
        inverseJoinColumns = @JoinColumn(name = "unid_id"))
    private Set<Unidade> unidades = new HashSet<>();
        
    // Métodos helper
    public void addPessoa(Pessoa pessoa) {
        this.pessoas.add(pessoa);
        pessoa.getEnderecos().add(this);
    }
    
    public void removePessoa(Pessoa pessoa) {
        this.pessoas.remove(pessoa);
        pessoa.getEnderecos().remove(this);
    }

    @Override
    public String toString() {
        return "Endereco{" +
               "id=" + id +
               ", logradouro='" + logradouro + '\'' +
               ", numero=" + numero +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return id != null && Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}