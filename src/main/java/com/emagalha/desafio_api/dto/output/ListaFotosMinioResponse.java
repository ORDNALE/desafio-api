package com.emagalha.desafio_api.dto.output;

import java.time.LocalDateTime;
import java.util.List;

public record ListaFotosMinioResponse(
    List<String> objetos,
    int total,
    LocalDateTime dataConsulta
) {}