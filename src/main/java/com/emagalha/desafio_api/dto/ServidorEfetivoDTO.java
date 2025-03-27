package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.ServidorEfetivo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoDTO {
    @Schema(example = "1", description = "ID do Servidor Efetivo")
    private Integer id;

    @NotBlank(message = "Matrícula é obrigatória")
    @Size(max = 20, message = "Matrícula deve ter no máximo 20 caracteres")
    private String matricula;

    @NotNull(message = "ID da pessoa é obrigatório")
    private Integer pessoaId;

    // Métodos de conversão
    public static ServidorEfetivoDTO fromEntity(ServidorEfetivo servidor) {
        return new ServidorEfetivoDTO(
            servidor.getId(),
            servidor.getMatricula(),
            servidor.getPessoa().getId()
        );
    }

    public ServidorEfetivo toEntity() {
        ServidorEfetivo servidor = new ServidorEfetivo();
        servidor.setMatricula(this.matricula);
        return servidor;
    }
}