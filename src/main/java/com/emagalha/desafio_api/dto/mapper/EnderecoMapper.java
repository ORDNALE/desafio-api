package com.emagalha.desafio_api.dto.mapper;

import org.springframework.stereotype.Component;

import com.emagalha.desafio_api.dto.output.EnderecoOutputDTO;
import com.emagalha.desafio_api.entity.Endereco;
import com.emagalha.desafio_api.dto.output.CidadeOutputDTO;

@Component
public class EnderecoMapper {

    public EnderecoOutputDTO toDTO(Endereco endereco) {
        return new EnderecoOutputDTO(
            endereco.getId(),
            endereco.getTipoLogradouro(),
            endereco.getLogradouro(),
            endereco.getNumero() != null ? endereco.getNumero().toString() : "S/N",
            endereco.getBairro(),
            new CidadeOutputDTO(
                endereco.getCidade().getId(),
                endereco.getCidade().getNome(),
                endereco.getCidade().getUf()
            )
        );
    }
}