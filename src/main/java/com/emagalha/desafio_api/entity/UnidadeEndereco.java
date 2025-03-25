package com.emagalha.desafio_api.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unidade_endereco")
public class UnidadeEndereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UnidadeEnderecoId id;

    @ManyToOne
    @MapsId("enderecoId")
    @JoinColumn(name = "end_id", nullable = false)
    private Endereco endereco;

    @ManyToOne
    @MapsId("unidadeId")
    @JoinColumn(name = "unid_id", nullable = false)
    private Unidade unidade;
}
