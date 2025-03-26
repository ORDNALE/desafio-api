package com.emagalha.desafio_api.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.dto.PessoaDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.PessoaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        // Validação adicional manual se necessário
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

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Transactional
    public PessoaDTO update(Integer id, PessoaDTO pessoaDTO) {
        try {
            Pessoa existingPessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));
            
            // Atualiza apenas os campos permitidos
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

    public void delete(Integer id) {
        Pessoa pessoa = findById(id);
        try {
            pessoaRepository.delete(pessoa);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Não é possível excluir a pessoa pois está sendo referenciada por outros registros");
        }
    }
}