package com.emagalha.desafio_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emagalha.desafio_api.dto.PessoaDTO;
import com.emagalha.desafio_api.dto.PessoaListDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;
import com.emagalha.desafio_api.repository.ServidorTemporarioRepository;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final LotacaoRepository lotacaoRepository;
    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final ServidorTemporarioRepository servidorTemporarioRepository;

    public PessoaService(PessoaRepository pessoaRepository,
                       LotacaoRepository lotacaoRepository,
                       ServidorEfetivoRepository servidorEfetivoRepository,
                       ServidorTemporarioRepository servidorTemporarioRepository) {
        this.pessoaRepository = pessoaRepository;
        this.lotacaoRepository = lotacaoRepository;
        this.servidorEfetivoRepository = servidorEfetivoRepository;
        this.servidorTemporarioRepository = servidorTemporarioRepository;
    }

    @Transactional
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        validatePessoaData(pessoaDTO);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoa.setSexo(pessoaDTO.getSexo());
        pessoa.setMae(pessoaDTO.getMae());
        pessoa.setPai(pessoaDTO.getPai());

        Pessoa savedEntity = pessoaRepository.save(pessoa);
        return PessoaDTO.fromEntity(savedEntity);
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findById(Integer id) {
        return pessoaRepository.findById(id)
                .map(PessoaDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<PessoaListDTO> findAll(Pageable pageable) {
        return pessoaRepository.findAll(pageable)
                .map(p -> new PessoaListDTO(
                        p.getId(),
                        p.getNome(),
                        p.getDataNascimento()
                ));
    }

    @Transactional
    public PessoaDTO update(Integer id, PessoaDTO pessoaDTO) {
        Pessoa existingPessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));

        validatePessoaData(pessoaDTO);

        existingPessoa.setNome(pessoaDTO.getNome());
        existingPessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        existingPessoa.setSexo(pessoaDTO.getSexo());
        existingPessoa.setMae(pessoaDTO.getMae());
        existingPessoa.setPai(pessoaDTO.getPai());

        return PessoaDTO.fromEntity(pessoaRepository.save(existingPessoa));
    }

    @Transactional
    public void delete(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));

        validateDeletionConstraints(pessoa);

        pessoaRepository.delete(pessoa);
    }

    private void validatePessoaData(PessoaDTO pessoaDTO) {
        if (pessoaDTO.getNome() == null || pessoaDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome da pessoa é obrigatório");
        }
    }

    private void validateDeletionConstraints(Pessoa pessoa) {
        List<String> constraints = new ArrayList<>();

        if (!pessoa.getFotos().isEmpty()) {
            constraints.add(pessoa.getFotos().size() + " foto(s)");
        }

        if (lotacaoRepository.existsByPessoaId(pessoa.getId())) {
            constraints.add("lotação(ões)");
        }

        if (servidorEfetivoRepository.existsById(pessoa.getId())) {
            constraints.add("servidor efetivo");
        }

        if (servidorTemporarioRepository.existsById(pessoa.getId())) {
            constraints.add("servidor temporário");
        }

        if (!constraints.isEmpty()) {
            throw new BusinessException("Não é possível excluir: pessoa vinculada a " + String.join(", ", constraints));
        }
    }

    @Transactional
    public String verificarVinculosEDeletar(Integer pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + pessoaId));
        
        // Lista para armazenar todos os vínculos encontrados
        List<String> vinculos = new ArrayList<>();
        
        // 1. Verifica Fotos (relacionamento OneToMany)
        if (!pessoa.getFotos().isEmpty()) {
            vinculos.add(pessoa.getFotos().size() + " foto(s)");
        }
        
        // 2. Verifica Lotação (via repository - mais eficiente que carregar a coleção)
        long lotacoesCount = lotacaoRepository.countByPessoaId(pessoaId);
        if (lotacoesCount > 0) {
            vinculos.add(lotacoesCount + " lotação(ões)");
        }
        
        // 3. Verifica Servidor Efetivo (relacionamento OneToOne)
        if (servidorEfetivoRepository.existsById(pessoaId)) {
            vinculos.add("servidor efetivo");
        }
        
        // 4. Verifica Servidor Temporário (relacionamento OneToOne)
        if (servidorTemporarioRepository.existsById(pessoaId)) {
            vinculos.add("servidor temporário");
        }
        
        // Se houver qualquer vínculo, lança exceção com a lista formatada
        if (!vinculos.isEmpty()) {
            String mensagemErro = "Não é possível excluir: pessoa vinculada a " + 
                String.join(", ", vinculos);
            throw new BusinessException(mensagemErro);
        }
        
        pessoaRepository.delete(pessoa);
        return "Pessoa (ID: " + pessoaId + ") excluída com sucesso.";
    }
}