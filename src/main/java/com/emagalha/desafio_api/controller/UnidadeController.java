package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.UnidadeDTO;
import com.emagalha.desafio_api.dto.UnidadeListDTO;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.simpleframework.xml.Path;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@Tag(name = "04 - Unidades", description = "API para gerenciamento de unidades")
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
    public ResponseEntity<UnidadeDTO> create(@Valid @RequestBody UnidadeDTO dto) {
        try {
            UnidadeDTO saved = service.save(dto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
            return ResponseEntity.created(location).body(saved);
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage()); // Será tratado pelo GlobalExceptionHandler
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar unidade por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade encontrada"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<UnidadeListDTO> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as unidades (paginado)")
    @ApiResponse(responseCode = "200", description = "Lista de unidades")
    public ResponseEntity<Page<UnidadeListDTO>> getAll(
        @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
            return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar unidade existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade atualizada"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<UnidadeDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody UnidadeDTO dto) {
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