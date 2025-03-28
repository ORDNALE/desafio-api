package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.ServidorEfetivoDTO;
import com.emagalha.desafio_api.dto.ServidorEfetivoListDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository servidorRepository;
    private final PessoaRepository pessoaRepository;
    private final LotacaoRepository lotacaoRepository;

    public ServidorEfetivoService(
        ServidorEfetivoRepository servidorRepository,
        PessoaRepository pessoaRepository,
        LotacaoRepository lotacaoRepository) {
        this.servidorRepository = servidorRepository;
        this.pessoaRepository = pessoaRepository;
        this.lotacaoRepository = lotacaoRepository;
    }

    @Transactional
    public ServidorEfetivoDTO save(ServidorEfetivoDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        if (pessoa.getServidorEfetivo() != null || pessoa.getServidorTemporario() != null) {
            throw new BusinessException("Esta pessoa já está vinculada a outro tipo de servidor");
        }

        if (servidorRepository.existsByMatricula(dto.getMatricula())) {
            throw new BusinessException("Matrícula '" + dto.getMatricula() + "' já está em uso");
        }

        ServidorEfetivo servidor = dto.toEntity();
        servidor.setPessoa(pessoa);

        ServidorEfetivo saved = servidorRepository.save(servidor);
        return ServidorEfetivoDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public Page<ServidorEfetivoListDTO> findAll(Pageable pageable) {
        return servidorRepository.findAll(pageable)
            .map(ServidorEfetivoListDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Optional<ServidorEfetivoListDTO> findById(Integer id) {
        return servidorRepository.findById(id)
            .map(ServidorEfetivoListDTO::fromEntity);
    }

    @Transactional
    public ServidorEfetivoDTO update(Integer id, ServidorEfetivoDTO dto) {
        ServidorEfetivo servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado com ID: " + id));
    
        if (!servidor.getMatricula().equals(dto.getMatricula()) && 
            servidorRepository.existsByMatricula(dto.getMatricula())) {
            throw new BusinessException("Matrícula '" + dto.getMatricula() + "' já está em uso");
        }
    
        servidor.setMatricula(dto.getMatricula());
    
        ServidorEfetivo updated = servidorRepository.save(servidor);
        return ServidorEfetivoDTO.fromEntity(updated);
    }
    
    @Transactional
    public void delete(Integer id) {
        ServidorEfetivo servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado com ID: " + id));
        
        if (lotacaoRepository.existsByPessoaId(servidor.getPessoa().getId())) {
            throw new BusinessException("Não é possível excluir: servidor vinculado a lotações ativas");
        }

        servidorRepository.delete(servidor);
    }
}