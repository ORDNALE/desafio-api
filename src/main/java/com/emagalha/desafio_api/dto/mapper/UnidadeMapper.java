package com.emagalha.desafio_api.dto.mapper;

import org.springframework.stereotype.Component;

import com.emagalha.desafio_api.dto.input.UnidadeInputDTO;
import com.emagalha.desafio_api.dto.output.UnidadeOutputDTO;
import com.emagalha.desafio_api.entity.Unidade;

@Component
public class UnidadeMapper {

    public Unidade toEntity(UnidadeInputDTO dto) {
        Unidade unidade = new Unidade();
        unidade.setNome(dto.getNome());
        unidade.setSigla(dto.getSigla());
        return unidade;
    }

    public UnidadeOutputDTO toDTO(Unidade unidade) {
        return new UnidadeOutputDTO(
            unidade.getId(),
            unidade.getNome(),
            unidade.getSigla()
        );
    }

    public void updateFromDTO(UnidadeInputDTO dto, Unidade entity) {
        entity.setNome(dto.getNome());
        entity.setSigla(dto.getSigla());
    }
}