// package com.emagalha.desafio_api.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.emagalha.desafio_api.dto.ServidorTemporarioDTO;
// import com.emagalha.desafio_api.entity.ServidorTemporario;
// import com.emagalha.desafio_api.service.ServidorTemporarioService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/servidores-temporarios")
// @Tag(name = "Servidor Temporário", description = "API para gerenciamento de servidores temporários")
// public class ServidorTemporarioController {

//     private final ServidorTemporarioService servidorTemporarioService;

//     @Autowired
//     public ServidorTemporarioController(ServidorTemporarioService servidorTemporarioService) {
//         this.servidorTemporarioService = servidorTemporarioService;
//     }

//     @PostMapping
//     @Operation(summary = "Criar um novo servidor temporário")
//     @ApiResponses({
//         @ApiResponse(responseCode = "201", description = "Servidor temporário criado com sucesso"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<ServidorTemporario> create(@Valid @RequestBody ServidorTemporario servidorTemporario) {
//         ServidorTemporario saved = servidorTemporarioService.save(servidorTemporario);
//         return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Obter um servidor temporário pelo ID")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Servidor temporário encontrado"),
//         @ApiResponse(responseCode = "404", description = "Servidor temporário não encontrado")
//     })
//     public ResponseEntity<ServidorTemporario> getById(@PathVariable Integer id) {
//         return ResponseEntity.ok(servidorTemporarioService.findById(id));
//     }

//     @GetMapping
//     @Operation(summary = "Listar todos os servidores temporários")
//     @ApiResponse(responseCode = "200", description = "Lista de servidores temporários")
//     public ResponseEntity<List<ServidorTemporario>> getAll() {
//         return ResponseEntity.ok(servidorTemporarioService.findAll());
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Atualizar um servidor temporário existente")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "Servidor temporário atualizado com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Servidor temporário não encontrado"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     public ResponseEntity<ServidorTemporario> update(@PathVariable Integer id, @Valid @RequestBody ServidorTemporario servidorTemporario) {
//         return ResponseEntity.ok(servidorTemporarioService.update(id, servidorTemporario));
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Excluir um servidor temporário")
//     @ApiResponses({
//         @ApiResponse(responseCode = "204", description = "Servidor temporário excluído com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Servidor temporário não encontrado")
//     })
//     public ResponseEntity<Void> delete(@PathVariable Integer id) {
//         servidorTemporarioService.delete(id);
//         return ResponseEntity.noContent().build();
//     }
// }