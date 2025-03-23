package com.emagalha.desafio_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emagalha.desafio_api.entity.ServidorTemporario;
import com.emagalha.desafio_api.repository.ServidorTemporarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository repository;

    public List<ServidorTemporario> listarTodos() {
        return repository.findAll();
    }

    public Optional<ServidorTemporario> buscarPorId(int id) {
        return repository.findById(id);
    }

    public ServidorTemporario salvar(ServidorTemporario servidor) {
        return repository.save(servidor);
    }

    public void deletar(int id){
        repository.deleteById(id);
    }
    
}
