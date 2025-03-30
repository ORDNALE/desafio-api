package com.emagalha.desafio_api.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.emagalha.desafio_api.config.security.dto.LoginRequest;
import com.emagalha.desafio_api.config.security.dto.LoginResponse;

@RestController
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