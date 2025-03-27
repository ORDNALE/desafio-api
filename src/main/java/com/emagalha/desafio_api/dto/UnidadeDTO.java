package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.Unidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeDTO {
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    private String nome;

    @Size(max = 20, message = "Sigla deve ter no máximo 20 caracteres")
    private String sigla;

    // Métodos de conversão
    public static UnidadeDTO fromEntity(Unidade unidade) {
        return new UnidadeDTO(
            unidade.getId(),
            unidade.getNome(),
            unidade.getSigla()
        );
    }

    public Unidade toEntity() {
        Unidade unidade = new Unidade();
        unidade.setNome(this.nome);
        unidade.setSigla(this.sigla);
        return unidade;
    }
}