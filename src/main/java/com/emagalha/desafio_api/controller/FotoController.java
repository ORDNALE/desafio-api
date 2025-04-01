package com.emagalha.desafio_api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emagalha.desafio_api.dto.output.FotoUploadResponse;
import com.emagalha.desafio_api.service.FotoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/fotos")
@RequiredArgsConstructor
@Tag(
    name = "02 - BucketFotos",
    description = "Envia para MinIo e recebe link com recuperação de foto",
    extensions = @Extension(
        name = "x-order", 
        properties = @ExtensionProperty(name = "order", value ="02")
    )
)
public class FotoController {

    private final FotoService fotoService;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Upload realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro no upload do arquivo")
    })
    public ResponseEntity<FotoUploadResponse> uploadFile(
        @Parameter(description = "Arquivo a ser enviado")
        @RequestPart(value = "file")
        @Schema(type = "string", format = "binary") MultipartFile file) {
        try {
            FotoUploadResponse response = fotoService.uploadFoto(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // @GetMapping("/{fotoId}/url")
    // public ResponseEntity<FotoUrlResponse> gerarUrlTemporaria(@PathVariable Integer fotoId) throws Exception {
    //     return ResponseEntity.ok(fotoService.gerarUrlTemporaria(fotoId));
    // }



}