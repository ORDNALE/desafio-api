package com.emagalha.desafio_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emagalha.desafio_api.dto.LotacaoDTO;
import com.emagalha.desafio_api.service.LotacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/lotacao")
@Tag(name = "Lotação", description = "Endpoints para gerenciamento de lotações")
@RequiredArgsConstructor
public class LotacaoController {

    private final LotacaoService service;

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as lotações", description = "Retorna uma lista de todas as lotações cadastradas.")
    public ResponseEntity<List<LotacaoDTO>> listarTodos() {
        List<LotacaoDTO> lotacoes = service.listarTodos();
        return ResponseEntity.ok(lotacoes);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar lotação por ID", description = "Retorna uma lotação com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lotação encontrada"),
        @ApiResponse(responseCode = "404", description = "Lotação não encontrada")
    })
    public ResponseEntity<LotacaoDTO> buscarPorId(@PathVariable Integer id) {
        LotacaoDTO lotacao = service.buscarPorId(id);
        return ResponseEntity.ok(lotacao);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar uma nova lotação", description = "Inclui uma nova lotação com os dados fornecidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lotação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<LotacaoDTO> salvar(@RequestBody LotacaoDTO lotacaoDTO) {
        LotacaoDTO lotacaoSalva = service.salvar(lotacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(lotacaoSalva);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar uma lotação", description = "Atualiza os dados de uma lotação existente.")
    public ResponseEntity<LotacaoDTO> atualizar(@PathVariable Integer id, @RequestBody LotacaoDTO lotacaoDTO) {
        LotacaoDTO lotacaoAtualizada = service.atualizar(id, lotacaoDTO);
        return ResponseEntity.ok(lotacaoAtualizada);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar lotação por ID", description = "Remove uma lotação com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Lotação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Lotação não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}