package com.emagalha.desafio_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emagalha.desafio_api.dto.output.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.output.ServidorUnidadeDTO;
import com.emagalha.desafio_api.service.ServidorEfetivoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/listar-servidores")
@Tag(
    name = "05 - Consulta - Servidores",
    description = "API para consulta de servidores lotados",
    extensions = @Extension(
        name = "x-order", 
        properties = @ExtensionProperty(name = "order", value = "05")
    )
)
@RequiredArgsConstructor
public class ServidoresConsultaController {
    
    private final ServidorEfetivoService service;

    @GetMapping("/{unidadeId}")
    @Operation(summary = "Listar servidores efetivos por unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidores encontrados"),
        @ApiResponse(responseCode = "400", description = "ID da unidade inválido"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<Page<ServidorUnidadeDTO>> getByUnidade(
            @PathVariable Integer unidadeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<ServidorUnidadeDTO> result = service.findServidoresEfetivosPorUnidadeId(unidadeId, pageable);
        
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/endereco-funcional")
    @Operation(summary = "Buscar endereço funcional por nome do servidor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Endereços encontrados"),
        @ApiResponse(responseCode = "400", description = "Parâmetro 'nome' inválido")
    })
    public ResponseEntity<Page<EnderecoFuncionalDTO>> getEnderecoFuncional(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("nomeServidor").ascending());
        return ResponseEntity.ok(service.findEnderecoFuncionalByNomeServidor(nome, pageable));
    }
}