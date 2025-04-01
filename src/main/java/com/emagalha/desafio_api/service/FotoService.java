package com.emagalha.desafio_api.service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emagalha.desafio_api.dto.output.FotoUploadResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FotoService {
    private final MinioService minioService;
    //private final FotoPessoaRepository fotoPessoaRepository;

    public FotoUploadResponse uploadFoto(MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode ser nulo ou vazio");
        }

      
        String objectName = UUID.randomUUID() + "-" + 
                          Objects.requireNonNull(file.getOriginalFilename());
        
        try (InputStream fileStream = file.getInputStream()) {
            String urlTemporaria = minioService.uploadFoto(
                fileStream,
                objectName,
                file.getContentType()
            );

            return new FotoUploadResponse(
                objectName,                  
                file.getOriginalFilename(), 
                urlTemporaria,              
                LocalDateTime.now()         
            );
        } catch (Exception e) {
            throw new Exception("Falha ao fazer upload do arquivo: " + e.getMessage(), e);
        }
    }

    // public String recuperarObjectNamePorFotoId(Integer fotoId) throws Exception {
    //     return fotoPessoaRepository.findById(fotoId)
    //         .map(FotoPessoa::getHash)
    //         .orElseThrow(() -> new Exception("Foto não encontrada para o ID: " + fotoId));
    // }

    // public FotoUrlResponse gerarUrlTemporaria(Integer fotoId) throws Exception {
       
    //     String objectName = fotoPessoaRepository.findObjectNameById(fotoId);
    //     if (objectName == null) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada para o ID: " + fotoId);

    //     }

    //     // Chama o MinIO para gerar a URL temporária
    //     String url = minioService.gerarUrlTemporaria("minhas-imagens", objectName);

    //     return new FotoUrlResponse(url, LocalDateTime.now().plusMinutes(5));
    // }
    

    
}