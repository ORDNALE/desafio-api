package com.emagalha.desafio_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.emagalha.desafio_api.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    List<Pessoa> findByNomeContaining(String nome);
}