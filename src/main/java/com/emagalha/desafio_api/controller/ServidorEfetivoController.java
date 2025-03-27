package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.ServidorEfetivoDTO;
import com.emagalha.desafio_api.service.ServidorEfetivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/servidores-efetivos")
@Tag(name = "Servidor Efetivo", description = "API para gerenciamento de servidores efetivos")
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
        @ApiResponse(responseCode = "404", description = "Pessoa associada não encontrada")
    })
    public ResponseEntity<ServidorEfetivoDTO> create(@Valid @RequestBody ServidorEfetivoDTO dto) {
        ServidorEfetivoDTO saved = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    // Implementar GET, PUT, DELETE seguindo o mesmo padrão de PessoaController
}