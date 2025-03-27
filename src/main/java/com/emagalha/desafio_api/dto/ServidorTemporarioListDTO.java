package com.emagalha.desafio_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.emagalha.desafio_api.entity.ServidorTemporario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporarioListDTO {
    private Integer id;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private Integer pessoaId;

    public static ServidorTemporarioListDTO fromEntity(ServidorTemporario servidor) {
        return new ServidorTemporarioListDTO(
            servidor.getId(),
            servidor.getDataAdmissao(),
            servidor.getDataDemissao(),
            servidor.getPessoa().getId()
        );
    }
}