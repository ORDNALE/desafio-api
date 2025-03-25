package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.UnidadeDTO;
import com.emagalha.desafio_api.entity.Unidade;
import com.emagalha.desafio_api.exception.ResourceNotFoundException;
import com.emagalha.desafio_api.repository.UnidadeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;

    public List<UnidadeDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UnidadeDTO buscarPorId(Integer id) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade com ID " + id + " não encontrada"));
        return convertToDTO(unidade);
    }

    public UnidadeDTO salvar(UnidadeDTO unidadeDTO) {
        Unidade unidade = new Unidade();
        unidade.setNome(unidadeDTO.getNome());
        unidade.setSigla(unidadeDTO.getSigla());
        Unidade unidadeSalva = repository.save(unidade);
        return convertToDTO(unidadeSalva);
    }

    public UnidadeDTO atualizar(Integer id, UnidadeDTO unidadeDTO) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade com ID " + id + " não encontrada"));
        unidade.setNome(unidadeDTO.getNome());
        unidade.setSigla(unidadeDTO.getSigla());
        Unidade unidadeAtualizada = repository.save(unidade);
        return convertToDTO(unidadeAtualizada);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Unidade com ID " + id + " não encontrada");
        }
        repository.deleteById(id);
    }

    private UnidadeDTO convertToDTO(Unidade unidade) {
        UnidadeDTO dto = new UnidadeDTO();
        dto.setId(unidade.getId());
        dto.setNome(unidade.getNome());
        dto.setSigla(unidade.getSigla());
        return dto;
    }
}