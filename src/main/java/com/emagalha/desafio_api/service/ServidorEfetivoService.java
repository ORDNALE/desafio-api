package com.emagalha.desafio_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.dto.ServidorEfetivoDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final PessoaRepository pessoaRepository;

    @Autowired
    public ServidorEfetivoService(ServidorEfetivoRepository servidorEfetivoRepository, 
                                 PessoaRepository pessoaRepository) {
        this.servidorEfetivoRepository = servidorEfetivoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public ServidorEfetivo save(ServidorEfetivoDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        if (servidorEfetivoRepository.existsById(dto.getPessoaId())) {
            throw new BusinessException("Já existe um servidor efetivo para esta pessoa");
        }

        try {
            ServidorEfetivo servidor = new ServidorEfetivo();
            servidor.setPessoa(pessoa);
            servidor.setMatricula(dto.getMatricula());
            return servidorEfetivoRepository.save(servidor);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar servidor efetivo: " + e.getMessage());
        }
    }

    public ServidorEfetivo findById(Integer id) {
        return servidorEfetivoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado com ID: " + id));
    }

    public List<ServidorEfetivo> findAll() {
        return servidorEfetivoRepository.findAll();
    }

    public ServidorEfetivo update(Integer id, ServidorEfetivoDTO dto) {
        ServidorEfetivo servidor = findById(id);
        try {
            servidor.setMatricula(dto.getMatricula());
            return servidorEfetivoRepository.save(servidor);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao atualizar servidor efetivo: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        ServidorEfetivo servidor = findById(id);
        try {
            servidorEfetivoRepository.delete(servidor);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Não é possível excluir o servidor efetivo pois está sendo referenciado por outros registros");
        }
    }
}