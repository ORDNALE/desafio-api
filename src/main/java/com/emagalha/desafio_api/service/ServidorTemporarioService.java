package com.emagalha.desafio_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorTemporario;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorTemporarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository servidorTemporarioRepository;
    private final PessoaRepository pessoaRepository;

    @Autowired
    public ServidorTemporarioService(ServidorTemporarioRepository servidorTemporarioRepository,
                                    PessoaRepository pessoaRepository) {
        this.servidorTemporarioRepository = servidorTemporarioRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public ServidorTemporario save(ServidorTemporarioDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        if (servidorTemporarioRepository.existsById(dto.getPessoaId())) {
            throw new BusinessException("Já existe um servidor temporário para esta pessoa");
        }

        try {
            ServidorTemporario servidor = new ServidorTemporario();
            servidor.setPessoa(pessoa);
            servidor.setDataAdmissao(dto.getDataAdmissao());
            servidor.setDataDemissao(dto.getDataDemissao());
            return servidorTemporarioRepository.save(servidor);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar servidor temporário: " + e.getMessage());
        }
    }

    public ServidorTemporario findById(Integer id) {
        return servidorTemporarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));
    }

    public List<ServidorTemporario> findAll() {
        return servidorTemporarioRepository.findAll();
    }

    public ServidorTemporario update(Integer id, ServidorTemporarioDTO dto) {
        ServidorTemporario servidor = findById(id);
        try {
            servidor.setDataAdmissao(dto.getDataAdmissao());
            servidor.setDataDemissao(dto.getDataDemissao());
            return servidorTemporarioRepository.save(servidor);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao atualizar servidor temporário: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        ServidorTemporario servidor = findById(id);
        try {
            servidorTemporarioRepository.delete(servidor);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Não é possível excluir o servidor temporário pois está sendo referenciado por outros registros");
        }
    }
}