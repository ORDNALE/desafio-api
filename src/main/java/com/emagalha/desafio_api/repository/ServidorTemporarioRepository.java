package com.emagalha.desafio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emagalha.desafio_api.entity.ServidorTemporario;

public interface ServidorTemporarioRepository  extends JpaRepository<ServidorTemporario, Integer>{
    
}
