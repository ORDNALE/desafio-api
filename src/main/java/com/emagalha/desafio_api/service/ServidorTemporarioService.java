package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorTemporario;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorTemporarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository servidorRepository;
    private final PessoaRepository pessoaRepository;

    public ServidorTemporarioService(
        ServidorTemporarioRepository servidorRepository,
        PessoaRepository pessoaRepository) {
        this.servidorRepository = servidorRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public ServidorTemporarioDTO save(ServidorTemporarioDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        // Valida se a pessoa já é servidor efetivo ou temporário
        if (pessoa.getServidorEfetivo() != null || pessoa.getServidorTemporario() != null) {
            throw new BusinessException("Esta pessoa já está vinculada a outro tipo de servidor");
        }

        ServidorTemporario servidor = dto.toEntity();
        servidor.setPessoa(pessoa); // Associa a pessoa (e define o ID via @MapsId)

        try {
            ServidorTemporario saved = servidorRepository.save(servidor);
            return ServidorTemporarioDTO.fromEntity(saved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar servidor temporário: " + e.getRootCause().getMessage());
        }
    }

    public ServidorTemporario findById(Integer id) {
        return servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));
    }

    // Implementar update, delete, findAll conforme PessoaService
}