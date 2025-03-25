package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.*;
import com.emagalha.desafio_api.entity.*;
import com.emagalha.desafio_api.repository.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroCompletoService {
    private final PessoaRepository pessoaRepository;
    private final FotoPessoaRepository fotoPessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final PessoaEnderecoRepository pessoaEnderecoRepository;
    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final ServidorTemporarioRepository servidorTemporarioRepository;
    private final LotacaoRepository lotacaoRepository;
    private final UnidadeRepository unidadeRepository;
    private final UnidadeEnderecoRepository unidadeEnderecoRepository;
    private final CidadeRepository cidadeRepository;   

    @Transactional
    public void cadastrarCompleto(CadastroCompletoDTO cadastroCompletoDTO) {
        // Salvar Pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(cadastroCompletoDTO.getPessoa().getNome());
        pessoa.setDataNascimento(cadastroCompletoDTO.getPessoa().getDataNascimento());
        pessoa.setSexo(cadastroCompletoDTO.getPessoa().getSexo());
        pessoa.setMae(cadastroCompletoDTO.getPessoa().getMae());
        pessoa.setPai(cadastroCompletoDTO.getPessoa().getPai());
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        // Salvar Fotos da Pessoa
        for (FotoPessoaDTO fotoDTO : cadastroCompletoDTO.getFotos()) {
            FotoPessoa foto = new FotoPessoa();
            foto.setPessoa(pessoaSalva);
            foto.setFotoData(fotoDTO.getFotoData());
            foto.setFotoBucket(fotoDTO.getFotoBucket());
            foto.setFotoHash(fotoDTO.getFotoHash());
            fotoPessoaRepository.save(foto);
        }

        // Salvar Endereços da Pessoa
        for (EnderecoDTO enderecoDTO : cadastroCompletoDTO.getEnderecosPessoa()) {
            Endereco endereco = new Endereco();
            endereco.setTipoLogradouro(enderecoDTO.getTipoLogradouro());
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setBairro(enderecoDTO.getBairro());

            // Buscar a Cidade pelo ID
            Cidade cidade = cidadeRepository.findById(enderecoDTO.getCidadeId())
                    .orElseThrow(() -> new RuntimeException("Cidade com ID " + enderecoDTO.getCidadeId() + " não encontrada"));
            endereco.setCidade(cidade);

            Endereco enderecoSalvo = enderecoRepository.save(endereco);

            PessoaEndereco pessoaEndereco = new PessoaEndereco();
            pessoaEndereco.setPessoa(pessoaSalva);
            pessoaEndereco.setEndereco(enderecoSalvo);
            pessoaEnderecoRepository.save(pessoaEndereco);
        }

        // Salvar Servidor Efetivo
        if (cadastroCompletoDTO.getServidorEfetivo() != null) {
            ServidorEfetivo servidorEfetivo = new ServidorEfetivo();
            servidorEfetivo.setPessoa(pessoaSalva);
            servidorEfetivo.setMatricula(cadastroCompletoDTO.getServidorEfetivo().getMatricula());
            servidorEfetivoRepository.save(servidorEfetivo);
        }

        // Salvar Servidor Temporário
        if (cadastroCompletoDTO.getServidorTemporario() != null) {
            ServidorTemporario servidorTemporario = new ServidorTemporario();
            servidorTemporario.setPessoa(pessoaSalva);
            servidorTemporario.setDataAdmissao(cadastroCompletoDTO.getServidorTemporario().getDataAdmissao());
            servidorTemporario.setDataDemissao(cadastroCompletoDTO.getServidorTemporario().getDataDemissao());
            servidorTemporarioRepository.save(servidorTemporario);
        }

        // Salvar Lotações
        for (LotacaoDTO lotacaoDTO : cadastroCompletoDTO.getLotacoes()) {
            Lotacao lotacao = new Lotacao();
            lotacao.setPessoa(pessoaSalva);
            lotacao.setUnidade(lotacaoDTO.getUnidade());
            lotacao.setDataLotacao(lotacaoDTO.getDataLotacao());
            lotacao.setDataRemocao(lotacaoDTO.getDataRemocao());
            lotacao.setPortaria(lotacaoDTO.getPortaria());
            lotacaoRepository.save(lotacao);
        }

        // Salvar Unidades
        for (UnidadeDTO unidadeDTO : cadastroCompletoDTO.getUnidades()) {
            Unidade unidade = new Unidade();
            unidade.setNome(unidadeDTO.getNome());
            unidade.setSigla(unidadeDTO.getSigla());
            Unidade unidadeSalva = unidadeRepository.save(unidade);

            // Filtrar endereços da unidade atual
            List<EnderecoDTO> enderecosUnidade = cadastroCompletoDTO.getEnderecosUnidade().stream()
                    .filter(enderecoDTO -> enderecoDTO.getUnidadeId().equals(unidadeDTO.getId()))
                    .collect(Collectors.toList());

            // Salvar Endereços da Unidade
            for (EnderecoDTO enderecoDTO : enderecosUnidade) {
                Endereco endereco = new Endereco();
                endereco.setTipoLogradouro(enderecoDTO.getTipoLogradouro());
                endereco.setLogradouro(enderecoDTO.getLogradouro());
                endereco.setNumero(enderecoDTO.getNumero());
                endereco.setBairro(enderecoDTO.getBairro());

                // Buscar a Cidade pelo ID
                Cidade cidade = cidadeRepository.findById(enderecoDTO.getCidadeId())
                        .orElseThrow(() -> new RuntimeException("Cidade com ID " + enderecoDTO.getCidadeId() + " não encontrada"));
                endereco.setCidade(cidade);

                Endereco enderecoSalvo = enderecoRepository.save(endereco);

                UnidadeEndereco unidadeEndereco = new UnidadeEndereco();
                unidadeEndereco.setUnidade(unidadeSalva);
                unidadeEndereco.setEndereco(enderecoSalvo);
                unidadeEnderecoRepository.save(unidadeEndereco);
            }
        }
    }
}