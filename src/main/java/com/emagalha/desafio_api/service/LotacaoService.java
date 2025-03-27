package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.LotacaoDTO;
import com.emagalha.desafio_api.dto.LotacaoListDTO;
import com.emagalha.desafio_api.entity.*;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.*;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LotacaoService {

    private final LotacaoRepository lotacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final UnidadeRepository unidadeRepository;

    public LotacaoService(
        LotacaoRepository lotacaoRepository,
        PessoaRepository pessoaRepository,
        UnidadeRepository unidadeRepository
    ) {
        this.lotacaoRepository = lotacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public LotacaoDTO save(LotacaoDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        Unidade unidade = unidadeRepository.findById(dto.getUnidadeId())
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + dto.getUnidadeId()));

        Lotacao lotacao = dto.toEntity();
        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);

        try {
            Lotacao saved = lotacaoRepository.save(lotacao);
            return LotacaoDTO.fromEntity(saved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar lotação: " + e.getRootCause().getMessage());
        }
    }

    public List<LotacaoListDTO> findAll() {
        return lotacaoRepository.findAll()
            .stream()
            .map(LotacaoListDTO::fromEntity)
            .toList();
    }

    public LotacaoListDTO findById(Integer id) {
        Lotacao lotacao = lotacaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada"));
        return LotacaoListDTO.fromEntity(lotacao);
    }

    public LotacaoDTO update(Integer id, LotacaoDTO dto) {
        Lotacao existingLotacao = lotacaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada com ID: " + id));

        existingLotacao.setDataLotacao(dto.getDataLotacao());
        existingLotacao.setDataRemocao(dto.getDataRemocao());
        existingLotacao.setPortaria(dto.getPortaria());

        Lotacao updated = lotacaoRepository.save(existingLotacao);
        return LotacaoDTO.fromEntity(updated);
    }

    public void delete(Integer id) {
        Lotacao lotacao = lotacaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada com ID: " + id));
        try {
            lotacaoRepository.delete(lotacao);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao excluir lotação: " + e.getRootCause().getMessage());
        }
    }
}