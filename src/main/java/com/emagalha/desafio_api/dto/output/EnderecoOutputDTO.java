package com.emagalha.desafio_api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoOutputDTO {
    private Integer id;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;
    private CidadeOutputDTO cidade;
}