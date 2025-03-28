package com.emagalha.desafio_api.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.dto.PessoaDTO;
import com.emagalha.desafio_api.dto.PessoaListDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final LotacaoRepository lotacaoRepository;

    public PessoaService(PessoaRepository pessoaRepository, LotacaoRepository lotacaoRepository) {
        this.lotacaoRepository = lotacaoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        if (pessoaDTO.getNome() == null || pessoaDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome da pessoa é obrigatório");
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoa.setSexo(pessoaDTO.getSexo());
        pessoa.setMae(pessoaDTO.getMae());
        pessoa.setPai(pessoaDTO.getPai());

        try {
            Pessoa savedEntity = pessoaRepository.save(pessoa);
            return PessoaDTO.fromEntity(savedEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro de integridade ao salvar pessoa: " + e.getRootCause().getMessage());
        } catch (Exception e) {
            throw new BusinessException("Erro inesperado ao salvar pessoa: " + e.getMessage());
        }
    }

    public Pessoa findById(Integer id) {
        return pessoaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));
    }

    public List<PessoaListDTO> findAll() {
    return pessoaRepository.findAll().stream()
        .map(p -> new PessoaListDTO(
            p.getId(),
            p.getNome(),
            p.getDataNascimento()
        ))
        .toList();
    }

    @Transactional
    public PessoaDTO update(Integer id, PessoaDTO pessoaDTO) {
        try {
            Pessoa existingPessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));
            
            existingPessoa.setNome(pessoaDTO.getNome());
            existingPessoa.setDataNascimento(pessoaDTO.getDataNascimento());
            existingPessoa.setSexo(pessoaDTO.getSexo());
            existingPessoa.setMae(pessoaDTO.getMae());
            existingPessoa.setPai(pessoaDTO.getPai());
            
            Pessoa updatedPessoa = pessoaRepository.save(existingPessoa);
            return PessoaDTO.fromEntity(updatedPessoa);
            
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao atualizar pessoa: " + e.getRootCause().getMessage());
        }
    }

    public String delete(Integer id) {
        Pessoa pessoa = findById(id);
        try {
            pessoaRepository.delete(pessoa);
            return "Pessoa (ID: " + id + ") excluído com sucesso.";
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Não é possível excluir a pessoa pois está sendo referenciada por outros registros");
        }
    }

    @Transactional
    public String verificarVinculosEDeletar(Integer pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + pessoaId));
        
        StringBuilder bloqueios = new StringBuilder();
        
        // 1. Verifica Fotos (relacionamento OneToMany)
        if (!pessoa.getFotos().isEmpty()) {
            bloqueios.append(pessoa.getFotos().size()).append(" foto(s)");
        }
        
        // 2. Verifica Lotação (via repository)
        Integer lotacoesCount = lotacaoRepository.countByPessoaId(pessoaId);
        if (lotacoesCount > 0) {
            if (bloqueios.length() > 0) bloqueios.append(", ");
            bloqueios.append(lotacoesCount).append(" lotação(ões)");
        }
        
        // 3. Verifica Servidor Efetivo (relacionamento OneToOne)
        if (pessoa.getServidorEfetivo() != null) {
            if (bloqueios.length() > 0) bloqueios.append(", ");
            bloqueios.append("1 servidor efetivo");
        }
        
        // 4. Verifica Servidor Temporário (relacionamento OneToOne)
        if (pessoa.getServidorTemporario() != null) {
            if (bloqueios.length() > 0) bloqueios.append(", ");
            bloqueios.append("1 servidor temporário");
        }
        
        // Se houver qualquer vínculo, lança exceção
        if (bloqueios.length() > 0) {
            throw new BusinessException("Não é possível excluir: pessoa vinculada a " + bloqueios.toString());
        }
        
        pessoaRepository.delete(pessoa);
        return "Pessoa (ID: " + pessoaId + ") excluída com sucesso.";
    }
}