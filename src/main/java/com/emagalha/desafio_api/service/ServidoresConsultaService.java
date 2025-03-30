package com.emagalha.desafio_api.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.emagalha.desafio_api.dto.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.ServidorUnidadeDTO;
import org.springframework.transaction.annotation.Transactional;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServidoresConsultaService {

    private final ServidorEfetivoRepository servidorEfetivoRepository;


    public Page<ServidorUnidadeDTO> findServidoresEfetivosPorUnidadeId(
            Integer unidId, Pageable pageable) {
        return servidorEfetivoRepository.findServidoresEfetivosPorUnidadeId(unidId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<EnderecoFuncionalDTO> findEnderecoFuncionalByNome(String nomeParcial, Pageable pageable) {
        return servidorEfetivoRepository
            .findEnderecoFuncionalByNomeServidor(nomeParcial, pageable);
    }
}