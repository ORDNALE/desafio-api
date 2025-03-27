package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.ServidorEfetivoDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository servidorRepository;
    private final PessoaRepository pessoaRepository;

    public ServidorEfetivoService(
        ServidorEfetivoRepository servidorRepository,
        PessoaRepository pessoaRepository) {
        this.servidorRepository = servidorRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public ServidorEfetivoDTO save(ServidorEfetivoDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        // Verifica se a pessoa já é um servidor efetivo/temporário
        if (pessoa.getServidorEfetivo() != null || pessoa.getServidorTemporario() != null) {
            throw new BusinessException("Esta pessoa já está vinculada a outro tipo de servidor");
        }

        ServidorEfetivo servidor = dto.toEntity();
        servidor.setPessoa(pessoa); // Associa a pessoa (e define o ID via @MapsId)

        try {
            ServidorEfetivo saved = servidorRepository.save(servidor);
            return ServidorEfetivoDTO.fromEntity(saved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar servidor: " + e.getRootCause().getMessage());
        }
    }

    public ServidorEfetivo findById(Integer id) {
        return servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor não encontrado com ID: " + id));
    }

    // Outros métodos (update, delete, findAll) seguindo o mesmo padrão de PessoaService
}