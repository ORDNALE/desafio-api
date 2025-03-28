package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.PessoaDTO;
import com.emagalha.desafio_api.dto.PessoaListDTO;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.service.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/pessoas")
@Tag(
    name = "01 - Pessoa",
    description = "API para gerenciamento de pessoas",
    extensions = @Extension(
        name = "x-order", 
        properties = @ExtensionProperty(name = "order", value = "1")
    )
)
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/incluir")
    @Operation(summary = "Criar uma nova pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<PessoaDTO> create(@Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO savedPessoa = pessoaService.save(pessoaDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPessoa.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedPessoa);
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Obter uma pessoa pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Pessoa> getById(@PathVariable Integer id) {
        return pessoaService.findById(id)
                .map(pessoaDTO -> ResponseEntity.ok(pessoaDTO.toEntity()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar-todos")
    @Operation(summary = "Listar todas as pessoas")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas")
    public ResponseEntity<List<PessoaListDTO>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pessoaService.findAll(pageable).getContent());
    }

    @PutMapping("/alterar/{id}")
    @Operation(summary = "Atualiza uma pessoa existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaDTO> update(
        @PathVariable Integer id, 
        @Valid @RequestBody PessoaDTO pessoaDTO) {
        
        PessoaDTO updatedPessoa = pessoaService.update(id, pessoaDTO);
        return ResponseEntity.ok(updatedPessoa);
    }

    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Excluir pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso"),
        @ApiResponse(responseCode = "409", description = "Pessoa vinculada a outros registros", 
                    content = @Content(schema = @Schema(example = "Não é possível excluir: pessoa vinculada a 2 lotação(ões) e 1 foto.")))
    })
    public ResponseEntity<String> deletePessoa(@PathVariable Integer id) {
        String mensagem = pessoaService.verificarVinculosEDeletar(id);
        return ResponseEntity.ok(mensagem);
    }
}