package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.UnidadeDTO;
import com.emagalha.desafio_api.dto.UnidadeListDTO;
import com.emagalha.desafio_api.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.simpleframework.xml.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@Tag(
    name = "04 - unidades",
    description = "API para gereciamento de unidades",
    extensions = @Extension(
        name = "x-order", 
        properties = @ExtensionProperty(name = "order", value = "4")
    )
)
public class UnidadeController {

    private final UnidadeService service;

    public UnidadeController(UnidadeService service) {
        this.service = service;
    }

    @PostMapping("/incluir")
    @Operation(summary = "Criar uma nova unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<UnidadeDTO> create(@Valid @RequestBody UnidadeDTO dto) {
        UnidadeDTO saved = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Buscar unidade por ID")
    public ResponseEntity<UnidadeListDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Path("/listar-todos")
    @Operation(summary = "Listar todas as unidades")
    public ResponseEntity<List<UnidadeListDTO>> getAll() {
    return ResponseEntity.ok(service.findAll());
}

    @PutMapping("/alterar/{id}")
    @Operation(summary = "Atualizar unidade existente")
    public ResponseEntity<UnidadeDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody UnidadeDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Excluir unidade")
    @ApiResponse(responseCode = "204", description = "Unidade excluída com sucesso")
    public ResponseEntity<String> deleteUnidade(@PathVariable Integer id) {
        String mensagem = service.deleteComVerificacao(id);
        return ResponseEntity.ok(mensagem);
    }
}