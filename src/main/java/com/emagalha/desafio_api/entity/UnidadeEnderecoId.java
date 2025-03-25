package com.emagalha.desafio_api.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UnidadeEnderecoId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "end_id")
    private Integer enderecoId;

    @Column(name = "unid_id")
    private Integer unidadeId;
}
