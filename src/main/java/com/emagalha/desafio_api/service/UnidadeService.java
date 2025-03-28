package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.UnidadeDTO;
import com.emagalha.desafio_api.dto.UnidadeListDTO;
import com.emagalha.desafio_api.entity.Unidade;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.UnidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final LotacaoRepository lotacaoRepository;

    public UnidadeService(UnidadeRepository unidadeRepository, 
                         LotacaoRepository lotacaoRepository) {
        this.unidadeRepository = unidadeRepository;
        this.lotacaoRepository = lotacaoRepository;
    }

    @Transactional
    public UnidadeDTO save(UnidadeDTO dto) {
        validarSiglaUnica(dto.getSigla());
        
        Unidade unidade = dto.toEntity();
        Unidade saved = unidadeRepository.save(unidade);
        return UnidadeDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public Page<UnidadeListDTO> findAll(Pageable pageable) {
        return unidadeRepository.findAll(pageable)
                .map(UnidadeListDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Optional<UnidadeListDTO> findById(Integer id) {
        return unidadeRepository.findById(id)
                .map(UnidadeListDTO::fromEntity);
    }

    @Transactional
    public UnidadeDTO update(Integer id, UnidadeDTO dto) {
        Unidade existingUnidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id));

        if (!existingUnidade.getSigla().equals(dto.getSigla())) {
            validarSiglaUnica(dto.getSigla());
        }

        existingUnidade.setNome(dto.getNome());
        existingUnidade.setSigla(dto.getSigla());

        return UnidadeDTO.fromEntity(unidadeRepository.save(existingUnidade));
    }

    @Transactional
    public void delete(Integer id) {
        if (!unidadeRepository.existsById(id)) {
            throw new EntityNotFoundException("Unidade não encontrada com ID: " + id);
        }
        
        if (lotacaoRepository.existsByUnidadeId(id)) {
            throw new BusinessException("Não é possível excluir: unidade possui lotações vinculadas");
        }
        
        unidadeRepository.deleteById(id);
    }

    private void validarSiglaUnica(String sigla) {
        if (sigla == null || sigla.isBlank()) {
            throw new BusinessException("Sigla não pode ser vazia");
        }
        
        if (unidadeRepository.existsBySiglaIgnoreCase(sigla.trim())) {
            throw new BusinessException("Já existe uma unidade com a sigla: " + sigla);
        }
    }
}