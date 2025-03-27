package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
import com.emagalha.desafio_api.entity.ServidorTemporario;
import com.emagalha.desafio_api.service.ServidorTemporarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/servidores-temporarios")
@Tag(name = "Servidor Temporário", description = "API para gerenciamento de servidores temporários")
public class ServidorTemporarioController {

    private final ServidorTemporarioService service;

    public ServidorTemporarioController(ServidorTemporarioService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar um novo servidor temporário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servidor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pessoa associada não encontrada")
    })
    public ResponseEntity<ServidorTemporarioDTO> create(@Valid @RequestBody ServidorTemporarioDTO dto) {
        ServidorTemporarioDTO saved = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    // Implementar GET, PUT, DELETE conforme PessoaController
}