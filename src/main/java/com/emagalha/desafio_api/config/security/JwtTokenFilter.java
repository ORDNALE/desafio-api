// package com.emagalha.desafio_api.config.security;

// import java.util.ArrayList;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import io.jsonwebtoken.io.IOException;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// public class JwtTokenFilter extends OncePerRequestFilter {

//     private final TokenService tokenService;

//     public JwtTokenFilter(TokenService tokenService) {
//         this.tokenService = tokenService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                   HttpServletResponse response,
//                                   FilterChain filterChain)
//             throws ServletException, IOException, java.io.IOException {
        
//         try {
//             String token = extractToken(request);
//             if (token != null && tokenService.isValidToken(token)) {
//                 String username = tokenService.getUsernameFromToken(token);
//                 UsernamePasswordAuthenticationToken auth = 
//                     new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//                 SecurityContextHolder.getContext().setAuthentication(auth);
//             }
//         } catch (Exception e) {
//             SecurityContextHolder.clearContext();
//             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
//             return;
//         }
        
//         filterChain.doFilter(request, response);
//     }

//     private String extractToken(HttpServletRequest request) {
//         String bearerToken = request.getHeader("Authorization");
//         if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//             return bearerToken.substring(7);
//         }
//         return null;
//     }
// }