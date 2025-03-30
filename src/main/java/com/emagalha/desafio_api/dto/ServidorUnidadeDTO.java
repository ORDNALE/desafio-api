package com.emagalha.desafio_api.dto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
    name = "ServidorUnidadeDTOMapping",
    classes = @ConstructorResult(
        targetClass = ServidorUnidadeDTO.class,
        columns = {
            @ColumnResult(name = "nome", type = String.class),
            @ColumnResult(name = "idade", type = Integer.class),
            @ColumnResult(name = "unidadeLotacao", type = String.class),
            @ColumnResult(name = "fotografia", type = String.class)
        }
    )
)
public class ServidorUnidadeDTO {
    private String nome;
    private Integer idade;
    private String unidadeLotacao;
    private String fotografia;

    // Construtor com todos os parâmetros
    public ServidorUnidadeDTO(String nome, Integer idade, String unidadeLotacao, String fotografia) {
        this.nome = nome;
        this.idade = idade;
        this.unidadeLotacao = unidadeLotacao;
        this.fotografia = fotografia;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getUnidadeLotacao() {
        return unidadeLotacao;
    }

    public String getFotografia() {
        return fotografia;
    }
}