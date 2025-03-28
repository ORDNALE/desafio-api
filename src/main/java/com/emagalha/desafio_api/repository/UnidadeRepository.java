package com.emagalha.desafio_api.repository;

import com.emagalha.desafio_api.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {
    Optional<Unidade> findByNomeAndSigla(String nome, String sigla);
    boolean existsBySigla(String sigla);
    boolean existsBySiglaIgnoreCase(String sigla);

}