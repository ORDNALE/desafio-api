package com.emagalha.desafio_api.dto.output;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FotoUploadResponse {
    private String objectName;      
    private String nomeOriginal;    
    private String urlTemporaria;   
    private LocalDateTime dataUpload; 
}