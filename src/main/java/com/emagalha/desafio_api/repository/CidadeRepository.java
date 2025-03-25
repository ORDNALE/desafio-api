package com.emagalha.desafio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emagalha.desafio_api.entity.Cidade;

public interface CidadeRepository  extends JpaRepository<Cidade, Integer> {

}
