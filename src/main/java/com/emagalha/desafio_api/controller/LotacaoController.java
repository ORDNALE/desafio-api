package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.LotacaoDTO;
import com.emagalha.desafio_api.dto.LotacaoListDTO;
import com.emagalha.desafio_api.service.LotacaoService;
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
@RequestMapping("/api/lotacoes")
@Tag(name = "4. Lotacao", description = "API para gerenciamento de lotações")
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
        @ApiResponse(responseCode = "404", description = "Pessoa ou Unidade não encontrada")
    })
    public ResponseEntity<LotacaoDTO> create(@Valid @RequestBody LotacaoDTO dto) {
        LotacaoDTO saved = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lotação por ID")
    public ResponseEntity<LotacaoListDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as lotações")
    public ResponseEntity<List<LotacaoListDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar lotação existente")
    public ResponseEntity<LotacaoDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody LotacaoDTO dto
    ) {
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