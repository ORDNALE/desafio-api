package com.emagalha.desafio_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class CadastroCompletoDTO {
    private PessoaDTO pessoa;
    private List<FotoPessoaDTO> fotos;
    private List<EnderecoDTO> enderecosPessoa;
    private ServidorEfetivoDTO servidorEfetivo;
    private ServidorTemporarioDTO servidorTemporario;
    private List<LotacaoDTO> lotacoes;
    private List<UnidadeDTO> unidades;
    private List<EnderecoDTO> enderecosUnidade;
}