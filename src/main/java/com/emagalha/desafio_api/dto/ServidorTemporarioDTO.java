package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.ServidorTemporario;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporarioDTO {
    private Integer id;

    @NotNull(message = "Data de admissão é obrigatória")
    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;

    @NotNull(message = "ID da pessoa é obrigatório")
    private Integer pessoaId;

    // Métodos de conversão
    public static ServidorTemporarioDTO fromEntity(ServidorTemporario servidor) {
        return new ServidorTemporarioDTO(
            servidor.getId(),
            servidor.getDataAdmissao(),
            servidor.getDataDemissao(),
            servidor.getPessoa().getId()
        );
    }

    public ServidorTemporario toEntity() {
        ServidorTemporario servidor = new ServidorTemporario();
        servidor.setDataAdmissao(this.dataAdmissao);
        servidor.setDataDemissao(this.dataDemissao);
        return servidor;
    }
}