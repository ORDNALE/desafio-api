package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.input.PessoaInputDTO;
import com.emagalha.desafio_api.dto.output.PessoaOutputDTO;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @PostMapping
    @Operation(summary = "Criar uma nova pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    })
    public ResponseEntity<PessoaOutputDTO> create(@Valid @RequestBody PessoaInputDTO pessoaInputDTO) {
        PessoaOutputDTO savedPessoa = pessoaService.save(pessoaInputDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPessoa.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedPessoa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma pessoa existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaOutputDTO> update(
        @PathVariable Integer id, 
        @Valid @RequestBody PessoaInputDTO pessoaInputDTO) {
        
        PessoaOutputDTO updatedPessoa = pessoaService.update(id, pessoaInputDTO);
        return ResponseEntity.ok(updatedPessoa);
}

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma pessoa pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaOutputDTO> getById(@PathVariable Integer id) {
        PessoaOutputDTO pessoa = pessoaService.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa com ID: " + id + " não encontrada."));
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas")
    public ResponseEntity<Page<PessoaOutputDTO>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pessoaService.findAll(pageable));
    }

    @DeleteMapping("/{id}")
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