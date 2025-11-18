package com.eam.LevelUpCorp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.repository.UserRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("🌐 Petición a: " + requestURI);

        String token = extractToken(request);

        if (token != null) {
            System.out.println("🔑 Token recibido: " + token.substring(0, Math.min(20, token.length())) + "...");

            if (jwtUtil.validateToken(token)) {
                System.out.println("✅ Token válido");
                String email = jwtUtil.extractEmail(token);
                System.out.println("📧 Email extraído: " + email);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Optional<UserEntity> userOpt = userRepository.findByEmail(email);

                    if (userOpt.isPresent()) {
                        UserEntity user = userOpt.get();
                        String roleFromDB = user.getRole();

                        System.out.println("👤 Usuario encontrado: " + email);
                        System.out.println("🎭 Rol en BD: '" + roleFromDB + "'");
                        System.out.println("🔤 Rol en mayúsculas: '" + roleFromDB.toUpperCase() + "'");

                        // ✅ CREAR AUTHORITIES BASADO EN EL ROL
                        String finalRole = "ROLE_" + roleFromDB.toUpperCase();
                        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                                new SimpleGrantedAuthority(finalRole)
                        );

                        System.out.println("✅ Authority creada: " + finalRole);
                        System.out.println("📋 Authorities: " + authorities);

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(email, null, authorities);

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        System.out.println("🔐 Usuario autenticado correctamente");
                        System.out.println("==========================================");
                    } else {
                        System.out.println("❌ Usuario no encontrado en BD para email: " + email);
                    }
                }
            } else {
                System.out.println("❌ Token inválido o expirado");
            }
        } else {
            System.out.println("⚠️ No se encontró token en la petición");
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}