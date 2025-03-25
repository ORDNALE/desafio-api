package com.emagalha.desafio_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.emagalha.desafio_api.dto.ServidorEfetivoDTO;
import com.emagalha.desafio_api.dto.ServidorEfetivoLotadoDTO;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.dto.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.service.ServidorEfetivoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/servidor-efetivo")
@Tag(name = "Servidor Efetivo", description = "Endpoints para gerenciamento de servidores efetivos")
@RequiredArgsConstructor
public class ServidorEfetivoController {

    private final ServidorEfetivoService service;

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os servidores", description = "Retorna uma lista de todos os servidores efetivos cadastrados.")
    public ResponseEntity<List<ServidorEfetivoLotadoDTO>> listarTodos() {
        List<ServidorEfetivoLotadoDTO> servidores = service.listarTodos();
        return ResponseEntity.ok(servidores);
    }


    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar servidor por ID", description = "Retorna um servidor efetivo com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servidor encontrado"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<ServidorEfetivoDTO> buscarPorId(@PathVariable Integer id) {
        ServidorEfetivo servidorEfetivo = service.buscarPorId(id);
        ServidorEfetivoDTO servidorEfetivoDTO = service.convertToDTO(servidorEfetivo);
        return ResponseEntity.ok(servidorEfetivoDTO);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar um novo servidor", description = "Inclui um novo servidor efetivo com os dados fornecidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servidor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ServidorEfetivoDTO> salvar(@RequestBody ServidorEfetivoDTO servidorDTO) {
        ServidorEfetivo servidorSalvo = service.salvar(servidorDTO);
        ServidorEfetivoDTO servidorEfetivoDTO = service.convertToDTO(servidorSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(servidorEfetivoDTO);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar um servidor", description = "Atualiza os dados de um servidor efetivo existente.")
    public ResponseEntity<ServidorEfetivoDTO> atualizar(@PathVariable Integer id, @RequestBody ServidorEfetivoDTO servidorDTO) {
        ServidorEfetivo servidorAtualizado = service.atualizar(id, servidorDTO);
        ServidorEfetivoDTO servidorEfetivoDTO = service.convertToDTO(servidorAtualizado);
        return ResponseEntity.ok(servidorEfetivoDTO);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar servidor por ID", description = "Remove um servidor efetivo com base no ID fornecido.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servidor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lotados/{unid_id}")
    @Operation(summary = "Buscar servidores lotados por unidade", description = "Retorna uma lista de servidores efetivos lotados em uma unidade específica.")
    public ResponseEntity<List<ServidorEfetivoLotadoDTO>> buscarServidoresLotados(@PathVariable int unid_id) {
        return ResponseEntity.ok(service.buscarServidoresLotados(unid_id));
    }

    @GetMapping("/endereco-funcional")
    @Operation(summary = "Consultar endereço funcional", description = "Retorna o endereço funcional da unidade onde o servidor está lotado, filtrando por parte do nome.")
    public ResponseEntity<List<EnderecoFuncionalDTO>> buscarEnderecoFuncional(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarEnderecoFuncional(nome));
    }

    @PostMapping("/upload-foto/{id}")
    @Operation(summary = "Upload de fotografia", description = "Realiza o upload da fotografia do servidor efetivo para o Min.IO")
    public ResponseEntity<String> uploadFoto(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        String url = service.uploadFoto(id, file);
        return ResponseEntity.ok(url);
    }
}