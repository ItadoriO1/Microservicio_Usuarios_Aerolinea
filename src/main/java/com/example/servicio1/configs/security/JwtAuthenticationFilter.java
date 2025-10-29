package com.example.servicio1.configs.security;

import com.example.servicio1.configs.token.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        // Ignorar endpoints públicos
        if (path.matches(".*/api/personas/login$") ||
                path.matches(".*/api/usuarios/save$") ||
                path.matches(".*/api/admins/save$") ||
                path.matches(".*/api/personas/me$") ||
                path.matches(".*/api/personas/email/[^/]+$") ||
                path.matches(".*/api/personas/update/\\d+$") ||
                path.matches(".*/api/personas/updatePassword/\\d+$")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Buscar cookie JWT_TOKEN
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        // Si no hay token o es inválido → 401
        if (token == null || !jwtUtil.isTokenValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o no proporcionado");
            return;
        }
        // Si el token es válido → autenticar
        String email = jwtUtil.extractEmail(token);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
