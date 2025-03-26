package com.emagalha.desafio_api.repository;

import com.emagalha.desafio_api.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    List<Endereco> findByLogradouroAndNumeroAndBairro(String logradouro, Integer numero, String bairro);
}