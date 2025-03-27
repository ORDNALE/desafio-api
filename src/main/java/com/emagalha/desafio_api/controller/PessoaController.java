package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.PessoaDTO;
import com.emagalha.desafio_api.dto.PessoaListDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.service.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "1. Pessoa", description = "API para gerenciamento de pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<PessoaDTO> create(@Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO savedPessoa = pessoaService.save(pessoaDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPessoa.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedPessoa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma pessoa pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Pessoa> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(pessoaService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas")
    public ResponseEntity<List<PessoaListDTO>> getAll() {
        return ResponseEntity.ok(pessoaService.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma pessoa existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaDTO> update(
        @PathVariable Integer id, 
        @Valid @RequestBody PessoaDTO pessoaDTO) {
        
        PessoaDTO updatedPessoa = pessoaService.update(id, pessoaDTO);
        return ResponseEntity.ok(updatedPessoa);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}