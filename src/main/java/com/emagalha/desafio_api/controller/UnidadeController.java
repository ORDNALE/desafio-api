// 'package com.emagalha.desafio_api.controller;

// import com.emagalha.desafio_api.dto.UnidadeDTO;
// import com.emagalha.desafio_api.entity.Unidade;
// import com.emagalha.desafio_api.service.UnidadeService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/unidades")
// @Tag(name = "Unidade", description = "API para gerenciamento de unidades")
// public class UnidadeController {

//     private final UnidadeService unidadeService;

//     public UnidadeController(UnidadeService unidadeService) {
//         this.unidadeService = unidadeService;
//     }

//     @PostMapping
//     @Operation(summary = "Criar uma nova unidade")
//     @ApiResponses({
//         @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<Unidade> create(@Valid @RequestBody Unidade unidade) {
//         Unidade saved = unidadeService.save(unidade);
//         return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Obter uma unidade pelo ID")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Unidade encontrada"),
//         @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
//     })
//     public ResponseEntity<Unidade> getById(@PathVariable Integer id) {
//         return ResponseEntity.ok(unidadeService.findById(id));
//     }

//     @GetMapping
//     @Operation(summary = "Listar todas as unidades")
//     @ApiResponse(responseCode = "200", description = "Lista de unidades")
//     public ResponseEntity<List<Unidade>> getAll() {
//         return ResponseEntity.ok(unidadeService.findAll());
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Atualizar uma unidade existente")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Unidade atualizada com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Unidade não encontrada"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<Unidade> update(@PathVariable Integer id, @Valid @RequestBody Unidade unidade) {
//         return ResponseEntity.ok(unidadeService.update(id, unidade));
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Excluir uma unidade")
//     @ApiResponses({
//         @ApiResponse(responseCode = "204", description = "Unidade excluída com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
//     })
//     public ResponseEntity<Void> delete(@PathVariable Integer id) {
//         unidadeService.delete(id);
//         return ResponseEntity.noContent().build();
//     }
// }