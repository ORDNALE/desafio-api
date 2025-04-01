package com.emagalha.desafio_api.controller;


import com.emagalha.desafio_api.dto.input.UnidadeInputDTO;
import com.emagalha.desafio_api.dto.output.UnidadeOutputDTO;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/unidades")
@Tag(
    name = "03 - Unidades",
    description = "API para gerenciamento de Unidades",
    extensions = @Extension(
        name = "x-order", 
        properties = @ExtensionProperty(name = "order", value = "03")
    )
)
public class UnidadeController {

    private final UnidadeService service;

    public UnidadeController(UnidadeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflito (ex: sigla já existe)")
    })
    public ResponseEntity<UnidadeOutputDTO> create(
            @Valid @RequestBody UnidadeInputDTO dto) {
        
        UnidadeOutputDTO saved = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar unidade por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade encontrada"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<UnidadeOutputDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id)));
    }

    @GetMapping
    @Operation(summary = "Listar todas as unidades (paginado)")
    @ApiResponse(responseCode = "200", description = "Lista de unidades")
    public ResponseEntity<Page<UnidadeOutputDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar unidade existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade atualizada"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<UnidadeOutputDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody UnidadeInputDTO  dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Unidade não encontrada para atualização: " + id);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Unidade excluída"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada"),
        @ApiResponse(responseCode = "409", description = "Conflito (ex: unidade com lotações ativas)")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}