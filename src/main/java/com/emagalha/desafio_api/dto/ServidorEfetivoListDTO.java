package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.ServidorEfetivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoListDTO {
    private Integer id;
    private String matricula;
    private Integer pessoaId;

    public static ServidorEfetivoListDTO fromEntity(ServidorEfetivo servidor) {
        return new ServidorEfetivoListDTO(
            servidor.getId(),
            servidor.getMatricula(),
            servidor.getPessoa().getId()
        );
    }
}