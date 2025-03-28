package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.LotacaoDTO;
import com.emagalha.desafio_api.dto.LotacaoListDTO;
import com.emagalha.desafio_api.service.LotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PostMapping("/incluir")
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

    @GetMapping("/listar/{id}")
    @Operation(summary = "Buscar lotação por ID")
    public ResponseEntity<LotacaoListDTO> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar-todos")
    @Operation(summary = "Listar todas as lotações")
    public ResponseEntity<List<LotacaoListDTO>> getAll(@RequestParam(defaultValue = "0") int page, 
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findAll(pageable).getContent());
    }

    @PutMapping("/alterar/{id}")
    @Operation(summary = "Atualizar lotação existente")
    public ResponseEntity<LotacaoDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody LotacaoDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Excluir lotação")
    @ApiResponse(responseCode = "204", description = "Lotação excluída com sucesso")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}