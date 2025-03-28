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
    private final MinioService minioService; // Para gerar URLs temporárias

    @Transactional(readOnly = true)
    public Page<ServidorUnidadeDTO> findServidoresByUnidade(Integer unidadeId, Pageable pageable) {
        Page<ServidorUnidadeDTO> result = servidorEfetivoRepository
            .findServidoresByUnidade(unidadeId, pageable);

        //URLS temporárias para as fotos
        result.getContent().forEach(dto -> {
            if (dto.fotoUrl() != null) {
                try {
                    dto = new ServidorUnidadeDTO(
                        dto.nome(),
                        dto.idade(),
                        dto.unidadeLotacao(),
                        minioService.gerarUrlTemporaria(dto.fotoUrl())
                    );
                } catch (Exception e) {
                    throw new RuntimeException("Falha ao gerar link temporário!", e);
                }
            }
        });
        
        return result;
    }

    @Transactional(readOnly = true)
    public Page<EnderecoFuncionalDTO> findEnderecoFuncionalByNome(String nomeParcial, Pageable pageable) {
        return servidorEfetivoRepository
            .findEnderecoFuncionalByNomeServidor(nomeParcial, pageable);
    }
}