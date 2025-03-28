package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.ServidorEfetivoDTO;
import com.emagalha.desafio_api.dto.ServidorEfetivoListDTO;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.service.ServidorEfetivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/servidores-efetivos")
@Tag(name = "02 - Servidor Efetivo", description = "API para servidores efetivos")
public class ServidorEfetivoController {

    private final ServidorEfetivoService service;

    public ServidorEfetivoController(ServidorEfetivoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar um novo servidor efetivo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servidor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflito (matrícula já existe)")
    })
    public ResponseEntity<ServidorEfetivoDTO> create(@Valid @RequestBody ServidorEfetivoDTO dto) {
        ServidorEfetivoDTO saved = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "Listar todos os servidores efetivos (paginado)")
    @ApiResponse(responseCode = "200", description = "Lista de servidores efetivos")
    public ResponseEntity<Page<ServidorEfetivoListDTO>> getAll(
        @PageableDefault(size = 10, sort = "pessoa.nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar servidor efetivo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor encontrado"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<ServidorEfetivoListDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor não encontrado")));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar servidor efetivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor atualizado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflito (matrícula já existe)")
    })
    public ResponseEntity<ServidorEfetivoDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody ServidorEfetivoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir servidor efetivo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servidor excluído"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflito (possui lotações ativas)")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}