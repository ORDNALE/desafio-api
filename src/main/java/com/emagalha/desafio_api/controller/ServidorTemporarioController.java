package com.emagalha.desafio_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
import com.emagalha.desafio_api.service.ServidorTemporarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/servidor-temporario")
@Tag(name = "Servidor Temporário", description = "Endpoints para gerenciamento de servidores temporários")
@RequiredArgsConstructor
public class ServidorTemporarioController {

    private final ServidorTemporarioService service;

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os servidores temporários", description = "Retorna uma lista de todos os servidores temporários cadastrados.")
    public ResponseEntity<List<ServidorTemporarioDTO>> listarTodos() {
        List<ServidorTemporarioDTO> servidores = service.listarTodos();
        return ResponseEntity.ok(servidores);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar servidor temporário por ID", description = "Retorna um servidor temporário com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor encontrado"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<ServidorTemporarioDTO> buscarPorId(@PathVariable Integer id) {
        ServidorTemporarioDTO servidorTemporario = service.buscarPorId(id);
        return ResponseEntity.ok(servidorTemporario);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar um novo servidor temporário", description = "Inclui um novo servidor temporário com os dados fornecidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servidor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ServidorTemporarioDTO> salvar(@RequestBody ServidorTemporarioDTO servidorDTO) {
        ServidorTemporarioDTO servidorSalvo = service.salvar(servidorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(servidorSalvo);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar um servidor temporário", description = "Atualiza os dados de um servidor temporário existente.")
    public ResponseEntity<ServidorTemporarioDTO> atualizar(@PathVariable Integer id, @RequestBody ServidorTemporarioDTO servidorDTO) {
        ServidorTemporarioDTO servidorAtualizado = service.atualizar(id, servidorDTO);
        return ResponseEntity.ok(servidorAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar servidor temporário por ID", description = "Remove um servidor temporário com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servidor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}