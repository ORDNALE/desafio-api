package com.emagalha.desafio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emagalha.desafio_api.entity.ServidorTemporario;

public interface ServidorTemporarioRepository extends JpaRepository<ServidorTemporario, Integer> {

    @Query("SELECT COUNT(l) > 0 FROM Lotacao l WHERE l.pessoa.id = :pessoaId")
    boolean existsLotacaoByPessoaId(@Param("pessoaId") Integer pessoaId);
    
    // Consulta para servidores ativos (dataDemissao nula ou futura)
    @Query("SELECT s FROM ServidorTemporario s WHERE s.dataDemissao IS NULL OR s.dataDemissao >= CURRENT_DATE")
    Page<ServidorTemporario> findAllAtivos(Pageable pageable);
}