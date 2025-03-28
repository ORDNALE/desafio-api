package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
import com.emagalha.desafio_api.dto.ServidorTemporarioListDTO;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorTemporario;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorTemporarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
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

    public ServidorTemporarioService(
        ServidorTemporarioRepository servidorRepository,
        PessoaRepository pessoaRepository,
        LotacaoRepository lotacaoRepository) {
        this.servidorRepository = servidorRepository;
        this.pessoaRepository = pessoaRepository;
        this.lotacaoRepository = lotacaoRepository;
    }

    @Transactional
    public ServidorTemporarioDTO save(ServidorTemporarioDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        if (pessoa.getServidorEfetivo() != null || pessoa.getServidorTemporario() != null) {
            throw new BusinessException("Esta pessoa já está vinculada a outro tipo de servidor");
        }

        validarDatasContrato(dto.getDataAdmissao(), dto.getDataDemissao());

        ServidorTemporario servidor = dto.toEntity();
        servidor.setPessoa(pessoa);

        ServidorTemporario saved = servidorRepository.save(servidor);
        return ServidorTemporarioDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public Page<ServidorTemporarioListDTO> findAll(Pageable pageable) {
        return servidorRepository.findAll(pageable)
            .map(ServidorTemporarioListDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Optional<ServidorTemporarioListDTO> findById(Integer id) {
        return servidorRepository.findById(id)
            .map(servidor -> {
                ServidorTemporarioListDTO dto = ServidorTemporarioListDTO.fromEntity(servidor);
                return dto;
            });
    }

    @Transactional
    public ServidorTemporarioDTO update(Integer id, ServidorTemporarioDTO dto) {
        ServidorTemporario servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));
    
        validarDatasContrato(dto.getDataAdmissao(), dto.getDataDemissao());
    
        servidor.setDataAdmissao(dto.getDataAdmissao());
        servidor.setDataDemissao(dto.getDataDemissao());
    
        return ServidorTemporarioDTO.fromEntity(servidorRepository.save(servidor));
    }
    
    @Transactional
    public void delete(Integer id) {
        ServidorTemporario servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));

        if (lotacaoRepository.existsByPessoaId(servidor.getPessoa().getId())) {
            throw new BusinessException("Não é possível excluir: servidor vinculado a lotações ativas");
        }

        servidorRepository.delete(servidor);
    }

    private void validarDatasContrato(LocalDate dataAdmissao, LocalDate dataDemissao) {
        if (dataAdmissao == null) {
            throw new BusinessException("Data de admissão é obrigatória");
        }
        
        if (dataDemissao != null && dataDemissao.isBefore(dataAdmissao)) {
            throw new BusinessException("Data de demissão não pode ser anterior à data de admissão");
        }
    }
}