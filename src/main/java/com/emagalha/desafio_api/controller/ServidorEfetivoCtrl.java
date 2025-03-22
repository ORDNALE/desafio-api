package com.emagalha.desafio_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emagalha.desafio_api.dto.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.ServidorEfetivoLotadoDTO;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.service.ServidorEfetivoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/servidor-efetivo")
@Tag(name = "Servidor Efetivo", description = "Endpoints para gerenciamento de servidores efetivos")
public class ServidorEfetivoCtrl {

    @Autowired
    private ServidorEfetivoService service;

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os servidores", description = "Retorna uma lista de todos os servidores efetivos cadastrados.")
    public List<ServidorEfetivo> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar servidor por ID", description = "Retorna um servidor efetivo com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ServidorEfetivo> buscarPorId(@PathVariable int id) {
        ServidorEfetivo servidor = service.buscarPorId(id);
        return ResponseEntity.ok(servidor);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar um novo servidor", description = "Inclui um novo servidor efetivo com os dados fornecidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servidor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ServidorEfetivo> salvar(@RequestBody ServidorEfetivo servidor) {
        ServidorEfetivo servidorSalvo = service.salvar(servidor);
        return ResponseEntity.status(HttpStatus.CREATED).body(servidorSalvo);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar servidor por ID", description = "Remove um servidor efetivo com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servidor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lotados/{unid_id}")
    public ResponseEntity<List<ServidorEfetivoLotadoDTO>> buscarServidoresLotados(@PathVariable int unid_id) {
        List<ServidorEfetivoLotadoDTO> servidores = service.buscarServidoresLotados(unid_id);
        return ResponseEntity.ok(servidores);
    }

    @GetMapping("/endereco-funcional")
    @Operation(summary = "Consultar endereço funcional por nome do servidor", description = "Retorna o endereço funcional da unidade onde o servidor está lotado, filtrando por parte do nome do servidor.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Endereço funcional encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Servidor ou unidade não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<EnderecoFuncionalDTO>> buscarEnderecoFuncional(@RequestParam String nome) {
        List<EnderecoFuncionalDTO> enderecos = service.buscarEnderecoFuncional(nome);
        return ResponseEntity.ok(enderecos);
    }
}