package com.emagalha.desafio_api.dto.mapper;

import org.springframework.stereotype.Component;

import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.Unidade;
import com.emagalha.desafio_api.dto.input.LotacaoInputDTO;
import com.emagalha.desafio_api.dto.output.LotacaoOutputDTO;
import com.emagalha.desafio_api.dto.output.PessoaOutputDTO;
import com.emagalha.desafio_api.dto.output.UnidadeOutputDTO;

@Component
public class LotacaoMapper {

    public Lotacao toEntity(LotacaoInputDTO dto, Pessoa pessoa, Unidade unidade) {
        Lotacao lotacao = new Lotacao();
        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);
        lotacao.setDataLotacao(dto.getDataLotacao());
        lotacao.setDataRemocao(dto.getDataRemocao());
        lotacao.setPortaria(dto.getPortaria());
        return lotacao;
    }

    public LotacaoOutputDTO toDTO(Lotacao lotacao) {
        return new LotacaoOutputDTO(
            lotacao.getId(),
            new PessoaOutputDTO(
                lotacao.getPessoa().getId(),
                lotacao.getPessoa().getNome(),
                lotacao.getPessoa().getDataNascimento(),
                lotacao.getPessoa().getSexo(),
                lotacao.getPessoa().getMae(),
                lotacao.getPessoa().getPai()
            ),
            new UnidadeOutputDTO(
                lotacao.getUnidade().getId(),
                lotacao.getUnidade().getNome(),
                lotacao.getUnidade().getSigla()
            ),
            lotacao.getDataLotacao(),
            lotacao.getDataRemocao(),
            lotacao.getPortaria()
        );
    }

    public void updateFromDTO(LotacaoInputDTO dto, Lotacao entity) {
        entity.setDataLotacao(dto.getDataLotacao());
        entity.setDataRemocao(dto.getDataRemocao());
        entity.setPortaria(dto.getPortaria());
    }
}