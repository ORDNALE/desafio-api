package com.emagalha.desafio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emagalha.desafio_api.entity.PessoaEndereco;
import com.emagalha.desafio_api.entity.PessoaEnderecoId;

public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, PessoaEnderecoId> {

}