package com.emagalha.desafio_api.dto;

public record EnderecoFuncionalDTO(
    String nomeServidor,
    String nomeUnidade,
    String logradouro,
    String numero,
    String bairro,
    String cidade,
    String uf
) {}