package com.emagalha.desafio_api.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.emagalha.desafio_api.dto.FotoUploadResponse;
import com.emagalha.desafio_api.dto.FotoUrlResponse;
import com.emagalha.desafio_api.service.FotoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fotos")
@Tag(name = "7. Fotos", description = "API para gerenciamento de fotos de pessoas")
@RequiredArgsConstructor
public class FotoController {
    private final FotoService fotoService;

    @PostMapping("/pessoas/{pessoaId}")
    @Operation(summary = "Upload de foto para uma pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Foto uploadada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Arquivo inválido"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<FotoUploadResponse> uploadFoto(
            @PathVariable Integer pessoaId,
            @RequestParam("file") @Valid @NotNull @NotBlank MultipartFile file) throws Exception {
        
        FotoUploadResponse response = fotoService.uploadFoto(pessoaId, file);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{fotoId}/url-temporaria")
    @Operation(summary = "Obter URL temporária para uma foto")
    @ApiResponse(responseCode = "200", description = "URL gerada com sucesso")
    public ResponseEntity<FotoUrlResponse> getTemporaryUrl(@PathVariable Integer fotoId) throws Exception {
        return ResponseEntity.ok(fotoService.gerarUrlTempraria(fotoId));
    }
}