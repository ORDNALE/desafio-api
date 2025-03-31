package com.emagalha.desafio_api.dto.mapper;

import com.emagalha.desafio_api.dto.input.PessoaInputDTO;
import com.emagalha.desafio_api.dto.output.PessoaOutputDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {

    public Pessoa toEntity(PessoaInputDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setSexo(dto.getSexo());
        pessoa.setMae(dto.getMae());
        pessoa.setPai(dto.getPai());
        return pessoa;
    }

    public PessoaOutputDTO toDTO(Pessoa pessoa) {
        return new PessoaOutputDTO(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getDataNascimento(),
            pessoa.getSexo(),
            pessoa.getMae(),
            pessoa.getPai()
        );
    }
}