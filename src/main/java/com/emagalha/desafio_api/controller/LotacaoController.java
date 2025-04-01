package com.emagalha.desafio_api.controller;


import com.emagalha.desafio_api.dto.input.LotacaoInputDTO;
import com.emagalha.desafio_api.dto.output.LotacaoOutputDTO;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.service.LotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/lotacoes")
@Tag(
    name = "05 - lotações",
    description = "API para gerenciamento de lotações",
    extensions = @Extension(
        name = "x-order", 
        properties = @ExtensionProperty(name = "order", value = "05")
    )
)
public class LotacaoController {

    private final LotacaoService service;

    public LotacaoController(LotacaoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova lotação")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lotação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pessoa ou unidade não encontrada")
    })
    public ResponseEntity<LotacaoOutputDTO> create(@Valid @RequestBody LotacaoInputDTO dto) {
        LotacaoOutputDTO saved = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lotação por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lotação encontrada",
                    content = @Content(schema = @Schema(implementation = LotacaoOutputDTO.class))),
        @ApiResponse(responseCode = "404", description = "Lotação não encontrada")
    })
    public ResponseEntity<LotacaoOutputDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada com ID: " + id)));
    }

    @GetMapping
    @Operation(summary = "Listar todas as lotações (paginado)")
    public ResponseEntity<Page<LotacaoOutputDTO>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size); 
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar lotação existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lotação atualizada com sucesso"),                    
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Lotação não encontrada"),
        @ApiResponse(responseCode = "409", description = "Conflito de datas (data de remoção anterior à data de lotação)")
    })
    public ResponseEntity<LotacaoOutputDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody LotacaoInputDTO dto) {
        
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir lotação")
    @ApiResponse(responseCode = "204", description = "Lotação excluída com sucesso")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}