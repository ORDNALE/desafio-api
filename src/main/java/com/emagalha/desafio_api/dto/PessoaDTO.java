package com.emagalha.desafio_api.dto;

import com.emagalha.desafio_api.entity.Pessoa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
    @Schema(example = "1", description = "ID Pessoa")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200)
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotBlank(message = "Sexo é obrigatório")
    @Size(max = 9)
    private String sexo;

    @Size(max = 200)
    private String mae;

    @Size(max = 200)
    private String pai;

    // Método de conversão para DTO
    public static PessoaDTO fromEntity(Pessoa pessoa) {
        return new PessoaDTO(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getDataNascimento(),
            pessoa.getSexo(),
            pessoa.getMae(),
            pessoa.getPai()
        );
    }

    // Método de conversão para Entity (usado no save e update)
    public Pessoa toEntity() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(this.id);
        pessoa.setNome(this.nome);
        pessoa.setDataNascimento(this.dataNascimento);
        pessoa.setSexo(this.sexo);
        pessoa.setMae(this.mae);
        pessoa.setPai(this.pai);
        return pessoa;
    }
}