package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoa")
@SequenceGenerator(name = "seq_pessoa", sequenceName = "seq_pessoa", allocationSize = 1)
public class Pessoa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
    @Column(name = "pes_id")
    private Integer id;
    
    @Column(name = "pes_nome", length = 200, nullable = false)
    private String nome;
    
    @Column(name = "pes_data_nascimento")
    private LocalDate dataNascimento;
    
    @Column(name = "pes_sexo", length = 9)
    private String sexo;
    
    @Column(name = "pes_mae", length = 200)
    private String mae;
    
    @Column(name = "pes_pai", length = 200)
    private String pai;
    
    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private ServidorEfetivo servidorEfetivo;
    
    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private ServidorTemporario servidorTemporario;
    
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FotoPessoa> fotos = new ArrayList<>();
    
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lotacao> lotacoes = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "pessoa_endereco",
        joinColumns = @JoinColumn(name = "pes_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id"))
    private Set<Endereco> enderecos = new HashSet<>();

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", dataNascimento=" + dataNascimento +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return id != null && Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Lotacao getLotacaoAtiva() {
        return lotacoes.stream()
            .filter(l -> l.getDataRemocao() == null)
            .findFirst()
            .orElse(null);
    }
}