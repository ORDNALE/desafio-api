package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.input.ServidorEfetivoInputDTO;
import com.emagalha.desafio_api.dto.mapper.ServidorEfetivoMapper;
import com.emagalha.desafio_api.dto.output.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.output.ServidorEfetivoOutputDTO;
import com.emagalha.desafio_api.dto.output.ServidorUnidadeDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;

import lombok.RequiredArgsConstructor;

import com.emagalha.desafio_api.repository.LotacaoRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository servidorRepository;
    private final PessoaRepository pessoaRepository;
    private final LotacaoRepository lotacaoRepository;
    private final ServidorEfetivoMapper servidorMapper;

    
    @Transactional
    public ServidorEfetivoOutputDTO save(ServidorEfetivoInputDTO dto) {

        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        if (pessoa.getServidorEfetivo() != null || pessoa.getServidorTemporario() != null) {
            throw new BusinessException("Esta pessoa já está vinculada a outro tipo de servidor");
        }

        if (servidorRepository.existsByMatricula(dto.getMatricula())) {
            throw new BusinessException("Matrícula já cadastrada.");
        }

        ServidorEfetivo servidor = new ServidorEfetivo();
        servidor.setPessoa(pessoa);
        servidor.setMatricula(dto.getMatricula());
        
        servidor = servidorRepository.save(servidor);
        
        return servidorMapper.toDTO(servidor);
    }

    @Transactional(readOnly = true)
    public Page<ServidorEfetivoOutputDTO> findAll(Pageable pageable) {
        return servidorRepository.findAll(pageable)
            .map(servidorMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<ServidorEfetivoOutputDTO> findById(Integer id) {
        return servidorRepository.findById(id)
            .map(servidorMapper::toDTO);
    }

    @Transactional
    public ServidorEfetivoOutputDTO update(Integer id, ServidorEfetivoInputDTO dto) {
        ServidorEfetivo servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado com ID: " + id));
    
        if (!servidor.getMatricula().equals(dto.getMatricula())) {
            validarMatriculaUnica(dto.getMatricula());
        }
    
        servidor.setMatricula(dto.getMatricula());
        ServidorEfetivo updated = servidorRepository.save(servidor);
        return servidorMapper.toDTO(updated);
    }


    private void validarMatriculaUnica(String matricula) {
        if (servidorRepository.existsByMatricula(matricula)) {
            throw new BusinessException("Matrícula '" + matricula + "' já está em uso");
        }
    }

    @Transactional(readOnly = true)
    public Page<ServidorUnidadeDTO> findServidoresEfetivosPorUnidadeId(Integer unidadeId, Pageable pageable) {
        return servidorRepository.findServidoresEfetivosPorUnidadeId(unidadeId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<EnderecoFuncionalDTO> findEnderecoFuncionalByNomeServidor(String nomeParcial, Pageable pageable) {
        return servidorRepository.findEnderecoFuncionalByNomeServidor(nomeParcial, pageable);
    }
    
    @Transactional
    public void delete(Integer id) {
        ServidorEfetivo servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor efetivo não encontrado com ID: " + id));
        
        if (lotacaoRepository.existsByPessoaId(servidor.getPessoa().getId())) {
            throw new BusinessException("Não é possível excluir: servidor vinculado a lotações ativas");
        }

        Pessoa pessoa = servidor.getPessoa();
        pessoa.setServidorEfetivo(null);
        pessoaRepository.save(pessoa);
        
        servidorRepository.delete(servidor);

        servidorRepository.delete(servidor);
    }
}