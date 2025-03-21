package com.emagalha.repository;

import com.emagalha.entity.Pessoa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    
    List<Pessoa> findByNomeIgnoreCase(String nome);
}