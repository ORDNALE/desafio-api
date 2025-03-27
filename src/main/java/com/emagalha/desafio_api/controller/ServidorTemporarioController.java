package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
import com.emagalha.desafio_api.dto.ServidorTemporarioListDTO;
import com.emagalha.desafio_api.service.ServidorTemporarioService;
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
@RequestMapping("/api/servidores-temporarios")
@Tag(name = "3. Servidor Temporário", description = "API para gerenciamento de servidores temporários")
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

    @GetMapping
    @Operation(summary = "Listar todos os servidores temporários")
    @ApiResponse(responseCode = "200", description = "Lista de servidores temporários")
    public ResponseEntity<List<ServidorTemporarioListDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar servidor temporário por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor encontrado"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<ServidorTemporarioListDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar servidor temporário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<ServidorTemporarioDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody ServidorTemporarioDTO dto
    ) {
        ServidorTemporarioDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir servidor temporário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado"),
        @ApiResponse(responseCode = "409", description = "Servidor vinculado a lotações")})
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        String mensagem = service.delete(id);
        return ResponseEntity.ok(mensagem);
    }
}