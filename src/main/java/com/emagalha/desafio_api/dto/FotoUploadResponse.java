package com.emagalha.desafio_api.dto;

import java.time.LocalDate;

public record FotoUploadResponse(
    Integer id,
    String nomeArquivo,
    String urlTemporaria,
    LocalDate dataUpload
) {}