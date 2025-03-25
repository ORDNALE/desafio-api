package com.emagalha.desafio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emagalha.desafio_api.entity.FotoPessoa;

public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Integer> {
    
}