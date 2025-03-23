package com.emagalha.desafio_api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.repository.LotacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LotacaoService {

    private  final LotacaoRepository repository;


    public List<Lotacao> listarTodos() { return repository.findAll(); }

    public Optional<Lotacao> buscarPorId(int id){
        return repository.findById(id);
    }

    public Lotacao salvar(Lotacao lotacao) { return repository.save(lotacao); }
    public void deletar(int id) { repository.deleteById(id); }



}