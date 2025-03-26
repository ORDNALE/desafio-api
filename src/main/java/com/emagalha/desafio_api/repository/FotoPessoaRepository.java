package com.emagalha.desafio_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emagalha.desafio_api.entity.FotoPessoa;
import com.emagalha.desafio_api.entity.Pessoa;

public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Integer> {
    List<FotoPessoa> findByPessoa(Pessoa pessoa);
    
}