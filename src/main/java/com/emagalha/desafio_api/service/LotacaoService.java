package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.LotacaoDTO;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.Unidade;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.UnidadeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LotacaoService {

    private final LotacaoRepository lotacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final UnidadeRepository unidadeRepository;

    public LotacaoService(LotacaoRepository lotacaoRepository,
                         PessoaRepository pessoaRepository,
                         UnidadeRepository unidadeRepository) {
        this.lotacaoRepository = lotacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public Lotacao save(LotacaoDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        Unidade unidade = unidadeRepository.findById(dto.getUnidadeId())
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + dto.getUnidadeId()));

        try {
            Lotacao lotacao = new Lotacao();
            lotacao.setPessoa(pessoa);
            lotacao.setUnidade(unidade);
            lotacao.setDataLotacao(dto.getDataLotacao());
            lotacao.setDataRemocao(dto.getDataRemocao());
            lotacao.setPortaria(dto.getPortaria());
            return lotacaoRepository.save(lotacao);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar lotação: " + e.getMessage());
        }
    }

    public Lotacao findById(Integer id) {
        return lotacaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada com ID: " + id));
    }

    public List<Lotacao> findAll() {
        return lotacaoRepository.findAll();
    }

    public Lotacao update(Integer id, LotacaoDTO dto) {
        Lotacao lotacao = findById(id);
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        Unidade unidade = unidadeRepository.findById(dto.getUnidadeId())
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + dto.getUnidadeId()));

        try {
            lotacao.setPessoa(pessoa);
            lotacao.setUnidade(unidade);
            lotacao.setDataLotacao(dto.getDataLotacao());
            lotacao.setDataRemocao(dto.getDataRemocao());
            lotacao.setPortaria(dto.getPortaria());
            return lotacaoRepository.save(lotacao);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao atualizar lotação: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        Lotacao lotacao = findById(id);
        try {
            lotacaoRepository.delete(lotacao);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Não é possível excluir a lotação pois está sendo referenciada por outros registros");
        }
    }

    public List<Lotacao> findByPessoaId(Integer pessoaId) {
        return lotacaoRepository.findByPessoaId(pessoaId);
    }

    public List<Lotacao> findByUnidadeId(Integer unidadeId) {
        return lotacaoRepository.findByUnidadeId(unidadeId);
    }
}