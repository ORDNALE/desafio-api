// package com.emagalha.desafio_api.controller;

// import com.emagalha.desafio_api.dto.LotacaoDTO;
// import com.emagalha.desafio_api.entity.Lotacao;
// import com.emagalha.desafio_api.service.LotacaoService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/lotacoes")
// @Tag(name = "Lotação", description = "API para gerenciamento de lotações")
// public class LotacaoController {

//     private final LotacaoService lotacaoService;

//     @Autowired
//     public LotacaoController(LotacaoService lotacaoService) {
//         this.lotacaoService = lotacaoService;
//     }

//     @PostMapping
//     @Operation(summary = "Criar uma nova lotação")
//     @ApiResponses({
//         @ApiResponse(responseCode = "201", description = "Lotação criada com sucesso"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<Lotacao> create(@Valid @RequestBody Lotacao lotacao) {
//         Lotacao saved = lotacaoService.save(lotacao);
//         return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Obter uma lotação pelo ID")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Lotação encontrada"),
//         @ApiResponse(responseCode = "404", description = "Lotação não encontrada")
//     })
//     public ResponseEntity<Lotacao> getById(@PathVariable Integer id) {
//         return ResponseEntity.ok(lotacaoService.findById(id));
//     }

//     @GetMapping
//     @Operation(summary = "Listar todas as lotações")
//     @ApiResponse(responseCode = "200", description = "Lista de lotações")
//     public ResponseEntity<List<Lotacao>> getAll() {
//         return ResponseEntity.ok(lotacaoService.findAll());
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Atualizar uma lotação existente")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Lotação atualizada com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Lotação não encontrada"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<Lotacao> update(@PathVariable Integer id, @Valid @RequestBody Lotacao lotacao) {
//         return ResponseEntity.ok(lotacaoService.update(id, lotacao));
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Excluir uma lotação")
//     @ApiResponses({
//         @ApiResponse(responseCode = "204", description = "Lotação excluída com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Lotação não encontrada")
//     })
//     public ResponseEntity<Void> delete(@PathVariable Integer id) {
//         lotacaoService.delete(id);
//         return ResponseEntity.noContent().build();
//     }

//     @GetMapping("/pessoa/{pessoaId}")
//     @Operation(summary = "Listar lotações por pessoa")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Lotações encontradas"),
//         @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
//     })
//     public ResponseEntity<List<Lotacao>> getByPessoaId(@PathVariable Integer pessoaId) {
//         return ResponseEntity.ok(lotacaoService.findByPessoaId(pessoaId));
//     }

//     @GetMapping("/unidade/{unidadeId}")
//     @Operation(summary = "Listar lotações por unidade")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Lotações encontradas"),
//         @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
//     })
//     public ResponseEntity<List<Lotacao>> getByUnidadeId(@PathVariable Integer unidadeId) {
//         return ResponseEntity.ok(lotacaoService.findByUnidadeId(unidadeId));
//     }
// }

//     @DeleteMapping("/deletar/{id}")
//     @Operation(summary = "Deletar lotação por ID", description = "Remove uma lotação com base no ID fornecido.")
//     @ApiResponses({
//         @ApiResponse(responseCode = "204", description = "Lotação deletada com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Lotação não encontrada")
//     })
//     public ResponseEntity<Void> deletar(@PathVariable Integer id) {
//         service.deletar(id);
//         return ResponseEntity.noContent().build();
//     }
// }