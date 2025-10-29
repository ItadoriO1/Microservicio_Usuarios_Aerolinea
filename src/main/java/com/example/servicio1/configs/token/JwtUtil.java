package com.example.servicio1.configs.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "J9#rT8!vL4@qP2$dW7%yB1^zM6&eN0*fS3+gJ5=hK8(uY2)R4{C7}A9<Z1>?D0|xV6~T5;O3_P8@qE2!mL9^F7&sH5+K2)N4{R8}V3<Y1>pB0|zC6~G9;X5@tJ7!aL2$dM4%fN8^wQ0=eS3+rH1(uT9)P5{oE7}Z2<iV6>yB3|cA8~D4;jO9_L1@kF0#rT7$pN5%bM2^gS4&eW3+qY6=hJ8(nU0)R9{C5}V7<X4>tB1|zK3~G2;L8@oD6!aF9$sM0%wE7^nH4=fP1+rS5(uQ2)T3{iY8}Z0<jV6>";
    private final long EXPIRATION_MS = 24 * 60 * 60 * 1000; // 1 día

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Long id) {
        return Jwts.builder()
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public String extractRol(String token) {
        return extractAllClaims(token).get("rol", String.class);
    }

    public Long extractId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }
}
