package com.emagalha.desafio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emagalha.desafio_api.entity.UnidadeEndereco;
import com.emagalha.desafio_api.entity.UnidadeEnderecoId;

public interface UnidadeEnderecoRepository  extends JpaRepository<UnidadeEndereco, UnidadeEnderecoId> {

}
