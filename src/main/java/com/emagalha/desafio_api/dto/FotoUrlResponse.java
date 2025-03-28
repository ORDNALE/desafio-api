package com.emagalha.desafio_api.dto;

import java.time.LocalDate;

public record FotoUrlResponse(
    String urlTemporaria,
    LocalDate dataExpiracao
) {}