package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.output.FotoUploadResponse;
import com.emagalha.desafio_api.dto.output.FotoUrlResponse;
import com.emagalha.desafio_api.dto.output.ListaFotosMinioResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FotoService {
    private final MinioService minioService;

    public FotoUploadResponse uploadFoto(MultipartFile file) {
        validarArquivo(file);

        String objectName = gerarNomeObjeto(file);
        String contentType = file.getContentType();

        try (InputStream fileStream = file.getInputStream()) {
            String urlTemporaria = minioService.uploadFoto(fileStream, objectName, contentType);

            return new FotoUploadResponse(
                objectName,
                file.getOriginalFilename(),
                urlTemporaria,
                LocalDateTime.now()
            );
        } catch (IOException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro ao ler o arquivo enviado"
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Falha no upload da foto: " + e.getMessage()
            );
        }
    }

    public FotoUrlResponse gerarUrlTemporariaPorNome(String objectName) {
        validarNomeObjeto(objectName);

        if (!minioService.objectExists(objectName)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Arquivo não encontrado: " + objectName
            );
        }

        try {
            String url = minioService.gerarUrlTemporaria(objectName, 5);
            return new FotoUrlResponse(url, LocalDateTime.now().plusMinutes(5));
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro ao gerar URL temporária: " + e.getMessage()
            );
        }
    }

    private void validarArquivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O arquivo não pode ser nulo ou vazio"
            );
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O nome do arquivo é inválido"
            );
        }
    }

    private void validarNomeObjeto(String objectName) {
        if (objectName == null || objectName.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O nome do objeto não pode ser vazio"
            );
        }
    }

    private String gerarNomeObjeto(MultipartFile file) {
        return UUID.randomUUID() + "-" + 
               Objects.requireNonNull(file.getOriginalFilename())
                     .replace(" ", "_");
    }

    public ListaFotosMinioResponse listarTodasImagens() {
        try {
            List<String> objetos = minioService.listarObjetos();
            return new ListaFotosMinioResponse(
                objetos,
                objetos.size(),
                LocalDateTime.now()
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro ao listar imagens: " + e.getMessage()
            );
        }
    }
}