package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.LotacaoDTO;
import com.emagalha.desafio_api.dto.LotacaoListDTO;
import com.emagalha.desafio_api.entity.*;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
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

    public LotacaoService(
        LotacaoRepository lotacaoRepository,
        PessoaRepository pessoaRepository,
        UnidadeRepository unidadeRepository) {
        this.lotacaoRepository = lotacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.unidadeRepository = unidadeRepository;
    }

    @Transactional
    public LotacaoDTO save(LotacaoDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        Unidade unidade = unidadeRepository.findById(dto.getUnidadeId())
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + dto.getUnidadeId()));

        validateLotacaoDates(dto.getDataLotacao(), dto.getDataRemocao());

        Lotacao lotacao = dto.toEntity();
        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);

        Lotacao saved = lotacaoRepository.save(lotacao);
        return LotacaoDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public Page<LotacaoListDTO> findAll(Pageable pageable) {
        return lotacaoRepository.findAll(pageable)
            .map(LotacaoListDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Optional<LotacaoListDTO> findById(Integer id) {
        return lotacaoRepository.findById(id)
            .map(LotacaoListDTO::fromEntity);
    }

    @Transactional
    public LotacaoDTO update(Integer id, LotacaoDTO dto) {
        Lotacao existingLotacao = lotacaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada com ID: " + id));

        validateLotacaoDates(dto.getDataLotacao(), dto.getDataRemocao());

        existingLotacao.setDataLotacao(dto.getDataLotacao());
        existingLotacao.setDataRemocao(dto.getDataRemocao());
        existingLotacao.setPortaria(dto.getPortaria());

        return LotacaoDTO.fromEntity(lotacaoRepository.save(existingLotacao));
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