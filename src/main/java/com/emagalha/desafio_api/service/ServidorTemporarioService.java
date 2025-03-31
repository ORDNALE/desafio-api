package com.emagalha.desafio_api.service;


import com.emagalha.desafio_api.dto.input.ServidorTemporarioInputDTO;
import com.emagalha.desafio_api.dto.mapper.ServidorTemporarioMapper;
import com.emagalha.desafio_api.dto.output.ServidorTemporarioOutputDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorTemporario;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorTemporarioRepository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository servidorRepository;
    private final PessoaRepository pessoaRepository;
    private final LotacaoRepository lotacaoRepository;
    private final ServidorTemporarioMapper mapper;

    public ServidorTemporarioService(
        ServidorTemporarioRepository servidorRepository,
        PessoaRepository pessoaRepository,
        LotacaoRepository lotacaoRepository,
        ServidorTemporarioMapper mapper) {
        this.servidorRepository = servidorRepository;
        this.pessoaRepository = pessoaRepository;
        this.lotacaoRepository = lotacaoRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ServidorTemporarioOutputDTO save(ServidorTemporarioInputDTO inputDTO) {
        Pessoa pessoa = pessoaRepository.findById(inputDTO.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + inputDTO.getPessoaId()));

        if (pessoa.getServidorEfetivo() != null || pessoa.getServidorTemporario() != null) {
            throw new BusinessException("Esta pessoa já está vinculada a outro tipo de servidor");
        }

        validarDatasContrato(inputDTO.getDataAdmissao(), inputDTO.getDataDemissao());

        ServidorTemporario servidor = mapper.toEntity(inputDTO);
        servidor.setPessoa(pessoa);
        
        ServidorTemporario savedServidor = servidorRepository.save(servidor);
        return mapper.toDTO(savedServidor);
    }

    @Transactional(readOnly = true)
    public Page<ServidorTemporarioOutputDTO> findAll(Pageable pageable) {
        return servidorRepository.findAll(pageable)
            .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<ServidorTemporarioOutputDTO> findById(Integer id) {
        return servidorRepository.findById(id)
            .map(mapper::toDTO);
    }

    @Transactional
    public ServidorTemporarioOutputDTO update(Integer id, ServidorTemporarioInputDTO inputDTO) {
        ServidorTemporario servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));
        
        validarDatasContrato(inputDTO.getDataAdmissao(), inputDTO.getDataDemissao());
        
        mapper.updateEntityFromDTO(inputDTO, servidor);
        
        if (inputDTO.getPessoaId() != null) {
            servidor.setPessoa(pessoaRepository.findById(inputDTO.getPessoaId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + inputDTO.getPessoaId())));
        }
    
        ServidorTemporario updatedServidor = servidorRepository.save(servidor);
        return mapper.toDTO(updatedServidor);
    }
    
    @Transactional
    public void delete(Integer id) {
        ServidorTemporario servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));

        if (lotacaoRepository.existsByPessoaId(servidor.getPessoa().getId())) {
            throw new BusinessException("Não é possível excluir: servidor vinculado a lotações ativas");
        }

        Pessoa pessoa = servidor.getPessoa();
        pessoa.setServidorTemporario(null);
        pessoaRepository.save(pessoa);
        servidorRepository.delete(servidor);
    }

    private void validarDatasContrato(LocalDate dataAdmissao, LocalDate dataDemissao) {
        if (dataAdmissao == null) {
            throw new IllegalArgumentException("Data de admissão é obrigatória");
        }
        
        if (dataDemissao != null && dataDemissao.isBefore(dataAdmissao)) {
            throw new IllegalArgumentException("Data de demissão não pode ser anterior à data de admissão");
        }
        
        if (dataDemissao != null && dataDemissao.isBefore(dataAdmissao)) {
            throw new BusinessException("Data de demissão não pode ser anterior à data de admissão");
        }
    }

    @Transactional(readOnly = true)
    public Page<ServidorTemporarioOutputDTO> findByPessoaNomeContaining(String nome, Pageable pageable) {
        return servidorRepository.findByPessoaNomeContainingIgnoreCase(nome, pageable)
            .map(mapper::toDTO);
    }
}