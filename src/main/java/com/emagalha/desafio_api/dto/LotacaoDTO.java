package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.Lotacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoDTO {
    @Schema(example = "1", description = "ID Lotação")
    private Integer id;

    @NotNull(message = "ID da pessoa é obrigatório")
    private Integer pessoaId;

    @NotNull(message = "ID da unidade é obrigatório")
    private Integer unidadeId;

    @NotNull(message = "Data de lotação é obrigatória")
    private LocalDate dataLotacao;

    private LocalDate dataRemocao;

    @Size(max = 100, message = "Portaria deve ter no máximo 100 caracteres")
    private String portaria;

    // Métodos de conversão
    public static LotacaoDTO fromEntity(Lotacao lotacao) {
        return new LotacaoDTO(
            lotacao.getId(),
            lotacao.getPessoa().getId(),
            lotacao.getUnidade().getId(),
            lotacao.getDataLotacao(),
            lotacao.getDataRemocao(),
            lotacao.getPortaria()
        );
    }

    public Lotacao toEntity() {
        Lotacao lotacao = new Lotacao();
        lotacao.setDataLotacao(this.dataLotacao);
        lotacao.setDataRemocao(this.dataRemocao);
        lotacao.setPortaria(this.portaria);
        return lotacao;
    }
}