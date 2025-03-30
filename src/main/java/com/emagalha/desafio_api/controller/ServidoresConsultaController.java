package com.emagalha.desafio_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emagalha.desafio_api.dto.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.ServidorUnidadeDTO;
import com.emagalha.desafio_api.service.ServidoresConsultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/servidores")
@Tag(name = "6. Consultas Customizadas", description = "Endpoints específicos do desafio")
public class ServidoresConsultaController {

    private final ServidoresConsultaService service;

    public ServidoresConsultaController(ServidoresConsultaService service) {
        this.service = service;
    }

    @GetMapping("/unidade/{unidadeId}")
    @Operation(summary = "Lista servidores efetivos por unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de servidores"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public Page<ServidorUnidadeDTO> getServidoresEfetivosPorUnidade(
            @PathVariable Integer unidId,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.findServidoresEfetivosPorUnidadeId(unidId, pageable);
    }

    @GetMapping("/endereco-funcional")
    @Operation(summary = "Busca endereço funcional por nome do servidor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de endereços"),
        @ApiResponse(responseCode = "400", description = "Parâmetro inválido")
    })
    public ResponseEntity<Page<EnderecoFuncionalDTO>> buscarEnderecoFuncional(
            @RequestParam @NotBlank String nome,
            @PageableDefault(size = 10) Pageable pageable) {
        
        return ResponseEntity.ok(
            service.findEnderecoFuncionalByNome(nome, pageable)
        );
    }
} 
