package com.emagalha.desafio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.Unidade;

public interface LotacaoRepository  extends JpaRepository<Lotacao, Integer>{
    boolean existsByPessoaId(Integer pessoaId);
    Integer countByUnidadeId(Integer unidadeId);

     boolean existsByUnidade(Unidade unidade);
    
     boolean existsByUnidadeId(Integer unidadeId);
     // Consulta para lotações ativas (dataRemocao nula ou futura)
    @Query("SELECT l FROM Lotacao l WHERE l.dataRemocao IS NULL OR l.dataRemocao >= CURRENT_DATE")
    Page<Lotacao> findAllAtivas(Pageable pageable);

    // Consulta por pessoa
    Page<Lotacao> findByPessoaId(Integer pessoaId, Pageable pageable);

    // Consulta por unidade
    Page<Lotacao> findByUnidadeId(Integer unidadeId, Pageable pageable);
     
     // Método para contar lotações ativas (se necessário para mensagens detalhadas)
     @Query("SELECT COUNT(l) > 0 FROM Lotacao l WHERE l.unidade = :unidade AND l.dataRemocao IS NULL")
     boolean existsByUnidadeAndAtiva(@Param("unidade") Unidade unidade);

    @Query("SELECT COUNT(l) FROM Lotacao l WHERE l.pessoa.id = :pessoaId")
    Integer countByPessoaId(@Param("pessoaId") Integer pessoaId);



}
