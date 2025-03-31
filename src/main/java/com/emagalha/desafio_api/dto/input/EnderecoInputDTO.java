package com.emagalha.desafio_api.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoInputDTO {
    @NotBlank
    private String tipoLogradouro;
    
    @NotBlank
    private String logradouro;
    
    private Integer numero;
    
    private String bairro;
    
    @NotNull
    private Integer cidadeId;
}