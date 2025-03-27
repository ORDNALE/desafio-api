package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.UnidadeDTO;
import com.emagalha.desafio_api.dto.UnidadeListDTO;
import com.emagalha.desafio_api.entity.Unidade;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public UnidadeDTO save(UnidadeDTO dto) {
        Unidade unidade = dto.toEntity();

        try {
            Unidade saved = unidadeRepository.save(unidade);
            return UnidadeDTO.fromEntity(saved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar unidade: " + e.getRootCause().getMessage());
        }
    }

    public List<UnidadeListDTO> findAll() {
        return unidadeRepository.findAll()
            .stream()
            .map(UnidadeListDTO::fromEntity)
            .toList();
    }

    public UnidadeListDTO findById(Integer id) {
        Unidade unidade = unidadeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
        return UnidadeListDTO.fromEntity(unidade);
    }

    public UnidadeDTO update(Integer id, UnidadeDTO dto) {
        Unidade existingUnidade = unidadeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id));

        existingUnidade.setNome(dto.getNome());
        existingUnidade.setSigla(dto.getSigla());

        Unidade updated = unidadeRepository.save(existingUnidade);
        return UnidadeDTO.fromEntity(updated);
    }

    public String delete(Integer id) {
        Unidade unidade = unidadeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id));
        try {
            unidadeRepository.delete(unidade);
            return "Unidade (ID: " + id + ") excluído com sucesso.";
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Não é possível excluir a unidade pois está vinculada a lotações.");
        }
    }
}