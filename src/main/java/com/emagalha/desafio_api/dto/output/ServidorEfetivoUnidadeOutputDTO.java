package com.emagalha.desafio_api.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoUnidadeOutputDTO {
    @Schema(example = "João da Silva", description = "Nome completo do servidor")
    private String nome;
    
    @Schema(example = "35", description = "Idade do servidor")
    private Integer idade;
    
    @Schema(example = "Secretaria Municipal de Educação", description = "Nome da unidade de lotação")
    private String unidadeLotacao;
    
    @Schema(example = "base64encodedstring", description = "Fotografia do servidor em base64")
    private String fotografia;
}