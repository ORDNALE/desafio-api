package com.emagalha.desafio_api.dto.output;

import java.time.LocalDateTime;

public record FotoUrlResponse(
    String url,
    LocalDateTime dataExpiracao
) {}