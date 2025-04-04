package com.emagalha.desafio_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.emagalha.desafio_api.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    boolean existsByNomeIgnoreCase(String nome);
}