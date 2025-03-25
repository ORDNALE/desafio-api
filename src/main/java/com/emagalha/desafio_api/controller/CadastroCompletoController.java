package com.emagalha.desafio_api.controller;

import com.emagalha.desafio_api.dto.CadastroCompletoDTO;
import com.emagalha.desafio_api.service.CadastroCompletoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cadastro-completo")
@RequiredArgsConstructor
public class CadastroCompletoController {

    private final CadastroCompletoService cadastroCompletoService;

    @PostMapping
    public ResponseEntity<Void> cadastrarCompleto(@RequestBody CadastroCompletoDTO cadastroCompletoDTO) {
        cadastroCompletoService.cadastrarCompleto(cadastroCompletoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}