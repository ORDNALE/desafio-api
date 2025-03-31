package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.input.LotacaoInputDTO;
import com.emagalha.desafio_api.dto.mapper.LotacaoMapper;
import com.emagalha.desafio_api.dto.output.LotacaoOutputDTO;
import com.emagalha.desafio_api.entity.*;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.*;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LotacaoService {

    private final LotacaoRepository lotacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final UnidadeRepository unidadeRepository;
    private final LotacaoMapper lotacaoMapper;

    public LotacaoService(
        LotacaoRepository lotacaoRepository,
        PessoaRepository pessoaRepository,
        UnidadeRepository unidadeRepository,
        LotacaoMapper lotacaoMapper) {
        this.lotacaoMapper = lotacaoMapper;
        this.lotacaoRepository = lotacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.unidadeRepository = unidadeRepository;
    }

     @Transactional
    public LotacaoOutputDTO save(LotacaoInputDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        Unidade unidade = unidadeRepository.findById(dto.getUnidadeId())
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + dto.getUnidadeId()));

        validateLotacaoDates(dto.getDataLotacao(), dto.getDataRemocao());

        Lotacao lotacao = lotacaoMapper.toEntity(dto, pessoa, unidade);
        Lotacao saved = lotacaoRepository.save(lotacao);
        return lotacaoMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public Page<LotacaoOutputDTO> findAll(Pageable pageable) {
        return lotacaoRepository.findAll(pageable)
            .map(lotacaoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<LotacaoOutputDTO> findById(Integer id) {
        return lotacaoRepository.findById(id)
            .map(lotacaoMapper::toDTO);
    }

    @Transactional
    public LotacaoOutputDTO update(Integer id, LotacaoInputDTO dto) {
        Lotacao existingLotacao = lotacaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada com ID: " + id));

        validateLotacaoDates(dto.getDataLotacao(), dto.getDataRemocao());

        lotacaoMapper.updateFromDTO(dto, existingLotacao);
        Lotacao updated = lotacaoRepository.save(existingLotacao);
        return lotacaoMapper.toDTO(updated);
    }


    @Transactional
    public void delete(Integer id) {
        if (!lotacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Lotação não encontrada com ID: " + id);
        }
        lotacaoRepository.deleteById(id);
    }

    private void validateLotacaoDates(LocalDate dataLotacao, LocalDate dataRemocao) {
        if (dataLotacao == null) {
            throw new BusinessException("Data de lotação é obrigatória");
        }
        
        if (dataRemocao != null && dataRemocao.isBefore(dataLotacao)) {
            throw new BusinessException("Data de remoção não pode ser anterior à data de lotação");
        }
    }
}