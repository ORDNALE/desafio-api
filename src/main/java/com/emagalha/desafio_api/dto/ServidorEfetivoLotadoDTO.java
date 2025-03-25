package com.emagalha.desafio_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidorEfetivoLotadoDTO {
    private Integer id;
    private String nome;
    private Integer idade;
    private String unidade;
    private String fotografia;
}
