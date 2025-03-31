// package com.emagalha.desafio_api.config.security;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.JwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Service;

// import javax.crypto.SecretKey;
// import java.util.Date;

// @Service
// public class TokenService {

//     private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
//     private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//     private final int EXPIRATION_MINUTES = 5;

//     public String generateToken(Authentication authentication) {
//         Date now = new Date();
//         Date expiration = new Date(now.getTime() + (EXPIRATION_MINUTES * 60 * 1000));
        
//         return Jwts.builder()
//             .setSubject(authentication.getName())
//             .setIssuedAt(now)
//             .setExpiration(expiration)
//             .signWith(SECRET_KEY)
//             .compact();
//     }

//     public boolean isValidToken(String token) {
//         try {
//             Jwts.parserBuilder()
//                 .setSigningKey(SECRET_KEY)
//                 .build()
//                 .parseClaimsJws(token);
//             return true;
//         } catch (ExpiredJwtException ex) {
//             logger.warn("Token expirado: {}", ex.getMessage());
//             return false;
//         } catch (JwtException | IllegalArgumentException ex) {
//             logger.warn("Token inválido: {}", ex.getMessage());
//             return false;
//         }
//     }
    
//     public String getUsernameFromToken(String token) {
//         Claims claims = Jwts.parserBuilder()
//             .setSigningKey(SECRET_KEY)
//             .build()
//             .parseClaimsJws(token)
//             .getBody();
//         return claims.getSubject();
//     }
// }