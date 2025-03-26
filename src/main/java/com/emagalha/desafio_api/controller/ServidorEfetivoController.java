// package com.emagalha.desafio_api.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.web.PageableDefault;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.emagalha.desafio_api.entity.ServidorEfetivo;
// import com.emagalha.desafio_api.service.ServidorEfetivoService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/servidores-efetivos")
// @Tag(name = "Servidor Efetivo", description = "API para gerenciamento de servidores efetivos")
// public class ServidorEfetivoController {

//     private final ServidorEfetivoService servidorEfetivoService;

//     @Autowired
//     public ServidorEfetivoController(ServidorEfetivoService servidorEfetivoService) {
//         this.servidorEfetivoService = servidorEfetivoService;
//     }

//     @PostMapping
//     @Operation(summary = "Criar um novo servidor efetivo")
//     @ApiResponses({
//         @ApiResponse(responseCode = "201", description = "Servidor efetivo criado com sucesso"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<ServidorEfetivo> create(@Valid @RequestBody ServidorEfetivo servidorEfetivo) {
//         ServidorEfetivo saved = servidorEfetivoService.save(servidorEfetivo);
//         return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Obter um servidor efetivo pelo ID")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Servidor efetivo encontrado"),
//         @ApiResponse(responseCode = "404", description = "Servidor efetivo não encontrado")
//     })
//     public ResponseEntity<ServidorEfetivo> getById(@PathVariable Integer id) {
//         return ResponseEntity.ok(servidorEfetivoService.findById(id));
//     }

//     @GetMapping
//     @Operation(summary = "Listar todos os servidores efetivos")
//     @ApiResponse(responseCode = "200", description = "Lista de servidores efetivos")
//     public ResponseEntity<List<ServidorEfetivo>> getAll() {
//         return ResponseEntity.ok(servidorEfetivoService.findAll());
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Atualizar um servidor efetivo existente")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Servidor efetivo atualizado com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Servidor efetivo não encontrado"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<ServidorEfetivo> update(@PathVariable Integer id, @Valid @RequestBody ServidorEfetivo servidorEfetivo) {
//         return ResponseEntity.ok(servidorEfetivoService.update(id, servidorEfetivo));
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Excluir um servidor efetivo")
//     @ApiResponses({
//         @ApiResponse(responseCode = "204", description = "Servidor efetivo excluído com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Servidor efetivo não encontrado")
//     })
//     public ResponseEntity<Void> delete(@PathVariable Integer id) {
//         servidorEfetivoService.delete(id);
//         return ResponseEntity.noContent().build();
//     }
// }