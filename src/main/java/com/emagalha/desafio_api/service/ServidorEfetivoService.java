package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.ServidorEfetivoDTO;
import com.emagalha.desafio_api.dto.ServidorEfetivoListDTO;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
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

        try {
            ServidorEfetivo saved = servidorRepository.save(servidor);
            return ServidorEfetivoDTO.fromEntity(saved);
        } catch (DataIntegrityViolationException e) {
            // Captura qualquer outro erro de integridade não tratado acima
            throw new BusinessException("Erro ao salvar servidor: " + e.getRootCause().getMessage());
        }
    }

    public List<ServidorEfetivoListDTO> findAll() {
        return servidorRepository.findAll().stream()
            .map(ServidorEfetivoListDTO::fromEntity)
            .toList();
    }

    public ServidorEfetivoListDTO findById(Integer id) {
        ServidorEfetivo servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado"));
        return ServidorEfetivoListDTO.fromEntity(servidor);
    }

    public ServidorEfetivoDTO update(Integer id, ServidorEfetivoDTO dto) {
        ServidorEfetivo servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado com ID: " + id));
    
        servidor.setMatricula(dto.getMatricula());
    
        ServidorEfetivo updated = servidorRepository.save(servidor);
        return ServidorEfetivoDTO.fromEntity(updated);
    }
    
    public String delete(Integer id) {
        ServidorEfetivo servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado com ID: " + id));
        
        // Verifica lotações pelo ID da pessoa
        List<Lotacao> lotacoes = lotacaoRepository.findByPessoaId(servidor.getPessoa().getId());
        if (!lotacoes.isEmpty()) {
            throw new BusinessException("Não é possível excluir: servidor vinculado a " + lotacoes.size() + " lotação(ões).");
        }

        try {
            servidorRepository.delete(servidor);
            return "Servidor efetivo (ID: " + id + ") excluído com sucesso.";
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro de integridade ao excluir servidor: " + e.getRootCause().getMessage());
        }
    }
}