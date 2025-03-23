package com.emagalha.desafio_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.entity.Unidade;
import com.emagalha.desafio_api.repository.UnidadeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;

    public List<Unidade> listarUnidades() {
        return repository.findAll();
    }

    public Optional<Unidade> buscarPorId(int id) {
        return repository.findById(id);
    }

    public Unidade salvar(Unidade unidade){
        return repository.save(unidade);
    }

    public void deletar(int id) {
        repository.deleteById(id);
    }
}
