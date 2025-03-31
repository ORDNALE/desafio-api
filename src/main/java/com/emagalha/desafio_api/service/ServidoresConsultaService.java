package com.emagalha.desafio_api.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.emagalha.desafio_api.dto.output.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.output.ServidorUnidadeDTO;

import org.springframework.transaction.annotation.Transactional;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServidoresConsultaService {
    private final ServidorEfetivoRepository servidorRepository;
    
    @Transactional(readOnly = true)
    public Page<ServidorUnidadeDTO> findServidoresEfetivosPorUnidadeId(Integer unidadeId, Pageable pageable) {
        if (unidadeId == null) {
            throw new IllegalArgumentException("ID da unidade não pode ser nulo");
        }
        return servidorRepository.findServidoresEfetivosPorUnidadeId(unidadeId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<EnderecoFuncionalDTO> findEnderecoFuncionalByNomeServidor(String nomeParcial, Pageable pageable) {
        if (nomeParcial == null || nomeParcial.trim().isEmpty()) {
            throw new IllegalArgumentException("Parâmetro 'nome' não pode ser vazio");
        }
        return servidorRepository.findEnderecoFuncionalByNomeServidor(nomeParcial, pageable);
    }
}