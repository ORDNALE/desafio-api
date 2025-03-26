package com.emagalha.desafio_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.dto.UnidadeDTO;
import com.emagalha.desafio_api.entity.Unidade;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.repository.UnidadeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    @Autowired
    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public Unidade save(UnidadeDTO dto) {
        try {
            Unidade unidade = new Unidade();
            mapDTOToEntity(dto, unidade);
            return unidadeRepository.save(unidade);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar unidade: " + e.getMessage());
        }
    }

    public Unidade findById(Integer id) {
        return unidadeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id));
    }

    public List<Unidade> findAll() {
        return unidadeRepository.findAll();
    }

    public Unidade update(Integer id, UnidadeDTO dto) {
        Unidade unidade = findById(id);
        try {
            mapDTOToEntity(dto, unidade);
            return unidadeRepository.save(unidade);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao atualizar unidade: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        Unidade unidade = findById(id);
        try {
            unidadeRepository.delete(unidade);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Não é possível excluir a unidade pois está sendo referenciada por outros registros");
        }
    }

    private void mapDTOToEntity(UnidadeDTO dto, Unidade entity) {
        entity.setNome(dto.getNome());
        entity.setSigla(dto.getSigla());
    }
}