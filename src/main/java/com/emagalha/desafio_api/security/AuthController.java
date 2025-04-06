package com.emagalha.desafio_api.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emagalha.desafio_api.security.dto.LoginRequest;
import com.emagalha.desafio_api.security.dto.LoginResponse;
import com.emagalha.desafio_api.service.TokenService;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "bearerAuth")
@RestController
@Tag(
    name = "01 - Endpoint de autenticação",
    description = "API para gerenciamento de autenticação",
    extensions = @Extension(
        name = "x-order", 
        properties = @ExtensionProperty(name = "order", value = "05")
    )
)
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
            request.login(), 
            request.password()
        );
        
        var auth = authenticationManager.authenticate(authenticationToken);
        var token = tokenService.generateToken(auth);
        
        return new LoginResponse(token);
    }
}