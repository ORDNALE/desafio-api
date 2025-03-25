package com.emagalha.desafio_api.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "servidor_efetivo")
@PrimaryKeyJoinColumn(name = "pes_id")
@EqualsAndHashCode(callSuper = true)
public class ServidorEfetivo extends Pessoa {
    
    @Column(name = "se_matricula", length = 20)
    private String matricula;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", insertable = false, updatable = false)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "servidor", fetch = FetchType.LAZY)
    private List<Lotacao> lotacoes;

    @OneToMany(mappedBy = "servidor", fetch = FetchType.LAZY)
    private List<FotoPessoa> fotos;


}
