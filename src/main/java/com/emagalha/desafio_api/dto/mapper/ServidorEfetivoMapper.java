package com.emagalha.desafio_api.dto.mapper;

import org.springframework.stereotype.Component;

import com.emagalha.desafio_api.dto.input.ServidorEfetivoInputDTO;
import com.emagalha.desafio_api.dto.output.PessoaOutputDTO;
import com.emagalha.desafio_api.dto.output.ServidorEfetivoOutputDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.ServidorEfetivo;

@Component
public class ServidorEfetivoMapper {

    public ServidorEfetivo toEntity(ServidorEfetivoInputDTO dto, Pessoa pessoa) {
        ServidorEfetivo servidor = new ServidorEfetivo();
        servidor.setId(dto.getPessoaId());
        servidor.setPessoa(pessoa);
        servidor.setMatricula(dto.getMatricula());
        return servidor;
    }

    public ServidorEfetivoOutputDTO toDTO(ServidorEfetivo servidor) {
        PessoaOutputDTO pessoaDTO = new PessoaOutputDTO(
            servidor.getPessoa().getId(),
            servidor.getPessoa().getNome(),
            servidor.getPessoa().getDataNascimento(),
            servidor.getPessoa().getSexo(),
            servidor.getPessoa().getMae(),
            servidor.getPessoa().getPai()
        );
        
        return new ServidorEfetivoOutputDTO(
            servidor.getId(),
            servidor.getMatricula(),
            pessoaDTO
        );
    }
}