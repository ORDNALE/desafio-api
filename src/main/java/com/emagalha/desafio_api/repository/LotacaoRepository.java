package com.emagalha.desafio_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.entity.Unidade;

public interface LotacaoRepository  extends JpaRepository<Lotacao, Integer>{
    List<Lotacao> findByUnidadeId(int unid_id);
    List<Lotacao> findByPessoaId(Integer pessoaId);
    List<Lotacao> findByPessoaAndUnidade(Pessoa pessoa, Unidade unidade);
    boolean existsByPessoaId(Integer pessoaId);
    Integer countByUnidadeId(Integer unidadeId);
    Integer countByPessoaId(Integer pessoaId);
}
