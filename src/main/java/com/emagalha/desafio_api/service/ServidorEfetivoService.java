package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.ServidorEfetivoLotadoDTO;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.exception.ResourceNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository repository;

    private final LotacaoRepository lotacaoRepository;

    private final PessoaRepository pessoaRepository;

    public List<ServidorEfetivo> listarTodos() {
        return repository.findAll();
    }

    public ServidorEfetivo buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor com ID " + id + " não encontrado"));
    }

    public ServidorEfetivo salvar(ServidorEfetivo servidor) {
        return repository.save(servidor);
    }

    public void deletar(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Servidor com ID " + id + " não encontrado");
        }
        repository.deleteById(id);
    } 

    public List<EnderecoFuncionalDTO> buscarEnderecoFuncional(String nome) {
    return pessoaRepository.findByNomeContaining(nome).stream() 
        .flatMap(pessoa -> pessoa.getLotacoes().stream())    
        .map(Lotacao::getUnidade)                              
        .flatMap(unidade -> unidade.getEnderecos().stream())    
        .map(endereco -> {                                    
            EnderecoFuncionalDTO dto = new EnderecoFuncionalDTO();
            dto.setLogradouro(endereco.getLogradouro());
            dto.setNumero(endereco.getNumero());
            dto.setBairro(endereco.getBairro());
            dto.setCidade(endereco.getCidade().getNome());
            dto.setUf(endereco.getCidade().getUf());
            return dto;
        })
        .collect(Collectors.toList());
    }

    public List<ServidorEfetivoLotadoDTO> buscarServidoresLotados(int unid_id) {
        return lotacaoRepository.findByUnidadeId(unid_id).stream() // 1. Stream de Lotacoes
            .map(Lotacao::getPessoa)                              // 2. Mapeia para Pessoas
            .filter(pessoa -> pessoa.getServidorEfetivo() != null) // 3. Filtra servidores efetivos
            .map(pessoa -> {                                      // 4. Mapeia para ServidorEfetivoLotadoDTO
                ServidorEfetivoLotadoDTO dto = new ServidorEfetivoLotadoDTO();
                dto.setNome(pessoa.getNome());
                dto.setIdade(calcularIdade(pessoa.getDataNascimento())); // Calcula a idade
                dto.setUnidadeLotacao(pessoa.getLotacoes().get(0).getUnidade().getNome());
    
                if (!pessoa.getFotos().isEmpty()) {
                    dto.setFotografia(pessoa.getFotos().get(0).getFotoBucket());
                } else {
                    dto.setFotografia(null);
                }
    
                return dto;
            })
            .collect(Collectors.toList());
    }

    // Método auxiliar para calcular a idade a partir da data de nascimento
    private int calcularIdade(Date dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }

        LocalDate dataNasc = dataNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNasc, hoje).getYears();
    }
}