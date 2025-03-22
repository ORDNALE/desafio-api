package com.emagalha.desafio_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emagalha.desafio_api.entity.Lotacao;

public interface LotacaoRepository  extends JpaRepository<Lotacao, Integer>{
    List<Lotacao> findByUnidadeId(int unid_id);
}
