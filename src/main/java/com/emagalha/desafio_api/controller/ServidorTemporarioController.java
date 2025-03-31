package com.emagalha.desafio_api.controller;


import com.emagalha.desafio_api.dto.input.ServidorTemporarioInputDTO;
import com.emagalha.desafio_api.dto.output.ServidorTemporarioOutputDTO;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.exception.ErrorResponse;
import com.emagalha.desafio_api.service.ServidorTemporarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/servidores-temporarios")
@Tag(name = "03 - Servidores Temporários", description = "API para servidores temporários")
public class ServidorTemporarioController {

    private final ServidorTemporarioService service;

    public ServidorTemporarioController(ServidorTemporarioService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar novo servidor temporário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servidor criado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflito (pessoa já vinculada)")
    })
    public ResponseEntity<ServidorTemporarioOutputDTO> create(
            @Valid @RequestBody ServidorTemporarioInputDTO inputDTO) {
        
        ServidorTemporarioOutputDTO saved = service.save(inputDTO);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "Listar servidores temporários (paginado)")
    @ApiResponse(responseCode = "200", description = "Lista de servidores temporários",
                content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<ServidorTemporarioOutputDTO>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "pessoa.nome") String sort,
        @RequestParam(required = false) String nome) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        if (nome != null && !nome.isEmpty()) {
            return ResponseEntity.ok(service.findByPessoaNomeContaining(nome, pageable));
        }
        
        return ResponseEntity.ok(service.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar servidor temporário por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor encontrado",
            content = @Content(schema = @Schema(implementation = ServidorTemporarioOutputDTO.class))),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServidorTemporarioOutputDTO> getById(@PathVariable Integer id) {
        ServidorTemporarioOutputDTO servidor = service.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Servidor temporário não encontrado com ID: " + id));
        return ResponseEntity.ok(servidor);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar servidor temporário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor atualizado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<ServidorTemporarioOutputDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody ServidorTemporarioInputDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir servidor temporário")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servidor excluído"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflito (possui lotações)")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}