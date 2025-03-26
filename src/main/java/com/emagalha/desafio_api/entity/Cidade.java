package com.emagalha.desafio_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cidade")
@SequenceGenerator(name = "seq_cidade", sequenceName = "seq_cidade", allocationSize = 1)
public class Cidade implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cidade")
    @Column(name = "cid_id")
    private Integer id;
    
    @Column(name = "cid_nome", length = 200, nullable = false)
    private String nome;
    
    @Column(name = "cid_uf", columnDefinition = "char(2)", nullable = false, length = 2)
    @Size(min = 2, max = 2)
    private String uf;
}