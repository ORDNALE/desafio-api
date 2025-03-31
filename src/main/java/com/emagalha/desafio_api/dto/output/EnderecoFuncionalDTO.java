package com.emagalha.desafio_api.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoFuncionalDTO {
    private String nomeServidor;   
    private String nomeUnidade;     
    private String logradouro;     
    private String numero;          
    private String bairro;          
    private String cidade;          
    private String uf;
    
}