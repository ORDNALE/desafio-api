package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.LotacaoDTO;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.exception.ResourceNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotacaoService {

    private final LotacaoRepository repository;

    public List<LotacaoDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LotacaoDTO buscarPorId(Integer id) {
        Lotacao lotacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lotação com ID " + id + " não encontrada"));
        return convertToDTO(lotacao);
    }

    public LotacaoDTO salvar(LotacaoDTO lotacaoDTO) {
        Lotacao lotacao = new Lotacao();
        lotacao.setId(lotacaoDTO.getId());
        lotacao.setPessoa(lotacaoDTO.getPessoa());
        lotacao.setUnidade(lotacaoDTO.getUnidade());
        lotacao.setDataLotacao(lotacaoDTO.getDataLotacao());
        lotacao.setDataRemocao(lotacaoDTO.getDataRemocao());
        lotacao.setPortaria(lotacaoDTO.getPortaria());
        Lotacao lotacaoSalva = repository.save(lotacao);
        return convertToDTO(lotacaoSalva);
    }

    public LotacaoDTO atualizar(Integer id, LotacaoDTO lotacaoDTO) {
        Lotacao lotacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lotação com ID " + id + " não encontrada"));
        lotacao.setPessoa(lotacaoDTO.getPessoa());
        lotacao.setUnidade(lotacaoDTO.getUnidade());
        lotacao.setDataLotacao(lotacaoDTO.getDataLotacao());
        lotacao.setDataRemocao(lotacaoDTO.getDataRemocao());
        lotacao.setPortaria(lotacaoDTO.getPortaria());
        Lotacao lotacaoAtualizada = repository.save(lotacao);
        return convertToDTO(lotacaoAtualizada);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Lotação com ID " + id + " não encontrada");
        }
        repository.deleteById(id);
    }

    private LotacaoDTO convertToDTO(Lotacao lotacao) {
        LotacaoDTO dto = new LotacaoDTO();
        dto.setId(lotacao.getId());
        dto.setPessoa(lotacao.getPessoa());
        dto.setUnidade(lotacao.getUnidade());
        dto.setDataLotacao(lotacao.getDataLotacao());
        dto.setDataRemocao(lotacao.getDataRemocao());
        dto.setPortaria(lotacao.getPortaria());
        return dto;
    }
}