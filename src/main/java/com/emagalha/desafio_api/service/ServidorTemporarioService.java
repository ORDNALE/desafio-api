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
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public ServidorTemporarioDTO save(ServidorTemporarioDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));

        // Valida se a pessoa já é servidor efetivo ou temporário
        if (pessoa.getServidorEfetivo() != null || pessoa.getServidorTemporario() != null) {
            throw new BusinessException("Esta pessoa já está vinculada a outro tipo de servidor");
        }

        ServidorTemporario servidor = dto.toEntity();
        servidor.setPessoa(pessoa);

        try {
            ServidorTemporario saved = servidorRepository.save(servidor);
            return ServidorTemporarioDTO.fromEntity(saved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao salvar servidor temporário: " + e.getRootCause().getMessage());
        }
    }

    public List<ServidorTemporarioListDTO> findAll() {
        return servidorRepository.findAll().stream()
            .map(ServidorTemporarioListDTO::fromEntity)
            .toList();
    }

    public ServidorTemporarioListDTO findById(Integer id) {
        ServidorTemporario servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado"));
        return ServidorTemporarioListDTO.fromEntity(servidor);
    }

    public ServidorTemporarioDTO update(Integer id, ServidorTemporarioDTO dto) {
        ServidorTemporario servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));
    
        // Atualiza apenas campos permitidos
        servidor.setDataAdmissao(dto.getDataAdmissao());
        servidor.setDataDemissao(dto.getDataDemissao());
    
        ServidorTemporario updated = servidorRepository.save(servidor);
        return ServidorTemporarioDTO.fromEntity(updated);
    }
    
    @Transactional
    public String delete(Integer id) {
        ServidorTemporario servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));

        // Verifica se há lotações vinculadas ao ID da PESSOA (não ao servidor temporário diretamente)
        List<Lotacao> lotacoes = lotacaoRepository.findByPessoaId(servidor.getPessoa().getId());
        if (!lotacoes.isEmpty()) {
            throw new BusinessException("Não é possível excluir: servidor vinculado a " + lotacoes.size() + " lotação(ões).");
        }

        try {
            servidorRepository.delete(servidor);
            return "Servidor temporário (ID: " + id + ") excluído com sucesso.";
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro de integridade ao excluir servidor: " + e.getRootCause().getMessage());
        }
    }

    
}