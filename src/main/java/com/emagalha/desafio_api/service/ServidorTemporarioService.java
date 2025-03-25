package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
import com.emagalha.desafio_api.entity.ServidorTemporario;
import com.emagalha.desafio_api.exception.ResourceNotFoundException;
import com.emagalha.desafio_api.repository.ServidorTemporarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository repository;

    public List<ServidorTemporarioDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ServidorTemporarioDTO buscarPorId(Integer id) {
        ServidorTemporario servidor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor Temporário com ID " + id + " não encontrado"));
        return convertToDTO(servidor);
    }

    public ServidorTemporarioDTO salvar(ServidorTemporarioDTO servidorDTO) {
        ServidorTemporario servidor = new ServidorTemporario();
        servidor.setId(servidorDTO.getPessoaId());
        servidor.setDataAdmissao(servidorDTO.getDataAdmissao());
        servidor.setDataDemissao(servidorDTO.getDataDemissao());
        ServidorTemporario servidorSalvo = repository.save(servidor);
        return convertToDTO(servidorSalvo);
    }

    public ServidorTemporarioDTO atualizar(Integer id, ServidorTemporarioDTO servidorDTO) {
        ServidorTemporario servidor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor Temporário com ID " + id + " não encontrado"));
        servidor.setDataAdmissao(servidorDTO.getDataAdmissao());
        servidor.setDataDemissao(servidorDTO.getDataDemissao());
        ServidorTemporario servidorAtualizado = repository.save(servidor);
        return convertToDTO(servidorAtualizado);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Servidor Temporário com ID " + id + " não encontrado");
        }
        repository.deleteById(id);
    }

    private ServidorTemporarioDTO convertToDTO(ServidorTemporario servidor) {
        ServidorTemporarioDTO dto = new ServidorTemporarioDTO();
        dto.setPessoaId(servidor.getId());
        dto.setDataAdmissao(servidor.getDataAdmissao());
        dto.setDataDemissao(servidor.getDataDemissao());
        return dto;
    }
}