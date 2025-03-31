package com.emagalha.desafio_api.dto.mapper;

import com.emagalha.desafio_api.dto.input.ServidorTemporarioInputDTO;
import com.emagalha.desafio_api.dto.output.ServidorTemporarioOutputDTO;
import com.emagalha.desafio_api.entity.ServidorTemporario;
import org.springframework.stereotype.Component;

@Component
public class ServidorTemporarioMapper {

    public ServidorTemporario toEntity(ServidorTemporarioInputDTO dto) {
        ServidorTemporario servidor = new ServidorTemporario();
        servidor.setDataAdmissao(dto.getDataAdmissao());
        servidor.setDataDemissao(dto.getDataDemissao());
        return servidor;
    }

    public ServidorTemporarioOutputDTO toDTO(ServidorTemporario entity) {
        ServidorTemporarioOutputDTO dto = new ServidorTemporarioOutputDTO();
        dto.setId(entity.getId());
        dto.setDataAdmissao(entity.getDataAdmissao());
        dto.setDataDemissao(entity.getDataDemissao());
        dto.setPessoa(entity.getPessoa() != null ? entity.getPessoa().getId() : null);
        return dto;
    }

    public void updateEntityFromDTO(ServidorTemporarioInputDTO dto, ServidorTemporario entity) {
        entity.setDataAdmissao(dto.getDataAdmissao());
        entity.setDataDemissao(dto.getDataDemissao());
    }
}