package com.emagalha.desafio_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emagalha.desafio_api.dto.UnidadeDTO;
import com.emagalha.desafio_api.service.UnidadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/unidade")
@Tag(name = "Unidade", description = "Endpoints para gerenciamento de unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService service;

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as unidades", description = "Retorna uma lista de todas as unidades cadastradas.")
    public ResponseEntity<List<UnidadeDTO>> listarTodos() {
        List<UnidadeDTO> unidades = service.listarTodos();
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar unidade por ID", description = "Retorna uma unidade com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade encontrada"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<UnidadeDTO> buscarPorId(@PathVariable Integer id) {
        UnidadeDTO unidade = service.buscarPorId(id);
        return ResponseEntity.ok(unidade);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar uma nova unidade", description = "Inclui uma nova unidade com os dados fornecidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<UnidadeDTO> salvar(@RequestBody UnidadeDTO unidadeDTO) {
        UnidadeDTO unidadeSalva = service.salvar(unidadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeSalva);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar uma unidade", description = "Atualiza os dados de uma unidade existente.")
    public ResponseEntity<UnidadeDTO> atualizar(@PathVariable Integer id, @RequestBody UnidadeDTO unidadeDTO) {
        UnidadeDTO unidadeAtualizada = service.atualizar(id, unidadeDTO);
        return ResponseEntity.ok(unidadeAtualizada);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar unidade por ID", description = "Remove uma unidade com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Unidade deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}