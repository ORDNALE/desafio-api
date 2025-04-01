package com.emagalha.desafio_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emagalha.desafio_api.entity.FotoPessoa;
import com.emagalha.desafio_api.entity.Pessoa;

public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Integer> {
    List<FotoPessoa> findByPessoa(Pessoa pessoa);
    
     @Query("SELECT f.hash FROM FotoPessoa f WHERE f.id = :fotoId")
    String findObjectNameById(@Param("fotoId") Integer fotoId);

    
}