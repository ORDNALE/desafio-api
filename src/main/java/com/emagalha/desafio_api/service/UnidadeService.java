package com.emagalha.desafio_api.service;


import com.emagalha.desafio_api.dto.input.UnidadeInputDTO;
import com.emagalha.desafio_api.dto.mapper.UnidadeMapper;
import com.emagalha.desafio_api.dto.output.UnidadeOutputDTO;
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
    private final UnidadeMapper unidadeMapper;

    public UnidadeService(UnidadeRepository unidadeRepository, 
                         LotacaoRepository lotacaoRepository,
                         UnidadeMapper unidadeMapper) {
        this.unidadeRepository = unidadeRepository;
        this.unidadeMapper = unidadeMapper;
        this.lotacaoRepository = lotacaoRepository;
    }

    @Transactional
    public UnidadeOutputDTO save(UnidadeInputDTO dto) {
        validarSiglaUnica(dto.getSigla());
        
        Unidade unidade = unidadeMapper.toEntity(dto);
        Unidade saved = unidadeRepository.save(unidade);
        return unidadeMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public Page<UnidadeOutputDTO> findAll(Pageable pageable) {
        return unidadeRepository.findAll(pageable)
                .map(unidadeMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<UnidadeOutputDTO> findById(Integer id) {
        return unidadeRepository.findById(id)
                .map(unidadeMapper::toDTO);
    }

    @Transactional
    public UnidadeOutputDTO update(Integer id, UnidadeInputDTO dto) {
        Unidade existingUnidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id));

        if (!existingUnidade.getSigla().equals(dto.getSigla())) {
            validarSiglaUnica(dto.getSigla());
        }

        unidadeMapper.updateFromDTO(dto, existingUnidade);
        Unidade updated = unidadeRepository.save(existingUnidade);
        return unidadeMapper.toDTO(updated);
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