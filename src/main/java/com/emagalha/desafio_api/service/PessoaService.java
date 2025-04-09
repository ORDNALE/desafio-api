package com.emagalha.desafio_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emagalha.desafio_api.dto.input.PessoaInputDTO;
import com.emagalha.desafio_api.dto.mapper.PessoaMapper;
import com.emagalha.desafio_api.dto.output.PessoaOutputDTO;
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
    private final PessoaMapper mapper;

    public PessoaService(PessoaRepository pessoaRepository,
                       LotacaoRepository lotacaoRepository,
                       ServidorEfetivoRepository servidorEfetivoRepository,
                       ServidorTemporarioRepository servidorTemporarioRepository,
                       PessoaMapper mapper) {
        this.pessoaRepository = pessoaRepository;
        this.lotacaoRepository = lotacaoRepository;
        this.servidorEfetivoRepository = servidorEfetivoRepository;
        this.servidorTemporarioRepository = servidorTemporarioRepository;
        this.mapper = mapper;
    }

    @Transactional
    public PessoaOutputDTO save(PessoaInputDTO pessoaInputDTO) {
        validatePessoaData(pessoaInputDTO); 
        
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaInputDTO.getNome());
        pessoa.setDataNascimento(pessoaInputDTO.getDataNascimento());
        pessoa.setSexo(pessoaInputDTO.getSexo());
        pessoa.setMae(pessoaInputDTO.getMae());
        pessoa.setPai(pessoaInputDTO.getPai());

        Pessoa savedEntity = pessoaRepository.save(pessoa);
        return mapper.toDTO(savedEntity);
    }

    @Transactional
    public PessoaOutputDTO update(Integer id, PessoaInputDTO pessoaInputDTO) {
        Pessoa existingPessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));

        validatePessoaData(pessoaInputDTO); 

        existingPessoa.setNome(pessoaInputDTO.getNome());
        existingPessoa.setDataNascimento(pessoaInputDTO.getDataNascimento());
        existingPessoa.setSexo(pessoaInputDTO.getSexo());
        existingPessoa.setMae(pessoaInputDTO.getMae());
        existingPessoa.setPai(pessoaInputDTO.getPai());

        Pessoa updatedEntity = pessoaRepository.save(existingPessoa);
        return mapper.toDTO(updatedEntity); 
    }

    @Transactional(readOnly = true)
    public Optional<PessoaOutputDTO> findById(Integer id) {
        return pessoaRepository.findById(id)
                .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<PessoaOutputDTO> findAll(Pageable pageable) {
        return pessoaRepository.findAll(pageable)
        .map(mapper::toDTO);
    }

    @Transactional
    public void delete(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));

        validateDeletionConstraints(pessoa);

        pessoaRepository.delete(pessoa);
    }

    private void validatePessoaData(PessoaInputDTO pessoaInputDTO) {
        if (pessoaInputDTO.getNome() == null || pessoaInputDTO.getNome().trim().isEmpty()) {
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