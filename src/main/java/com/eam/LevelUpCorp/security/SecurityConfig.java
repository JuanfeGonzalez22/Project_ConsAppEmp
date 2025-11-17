package com.eam.LevelUpCorp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(authz -> authz
            // ✅ RUTAS PÚBLICAS (todos pueden acceder)
            .requestMatchers(
                "/project/api/v1/auth/**",    // Login y registro
                "/project/swagger-ui/**",     // Swagger UI
                "/project/v3/api-docs/**",    // API Docs
                "/project/api-docs/**",
                "/project/error",
                
                // Rutas sin context-path
                "/api/v1/auth/**",
                "/swagger-ui/**", 
                "/v3/api-docs/**",
                "/api-docs/**"
            ).permitAll()
            
            // ✅ GESTIÓN DE USUARIOS - SOLO ADMIN
            .requestMatchers(
                "/project/api/v1/users/**",
                "/api/v1/users/**"
            ).hasRole("ADMIN")
            
            // ✅ GESTIÓN DE CURSOS - SOLO ADMIN (AGREGAR ESTO)
            .requestMatchers(
                "/project/api/v1/courses/**",
                "/api/v1/courses/**"
            ).hasRole("ADMIN")
            
            // ✅ GESTIÓN DE MÓDULOS - SOLO ADMIN (AGREGAR ESTO)
            .requestMatchers(
                "/project/api/v1/modules/**",
                "/api/v1/modules/**"
            ).hasRole("ADMIN")
            
            // ✅ RUTAS DE INSTRUCTOR (si las tienes)
            .requestMatchers(
                "/project/api/v1/instructor/**",
                "/api/v1/instructor/**"
            ).hasRole("INSTRUCTOR")
            
            // ✅ RUTAS DE APRENDIZ (si las tienes)
            .requestMatchers(
                "/project/api/v1/apprentice/**",
                "/api/v1/apprentice/**",
                "/project/api/v1/student/**",
                "/api/v1/student/**"
            ).hasRole("APRENDIZ")
            
            // ✅ EL RESTO REQUIERE CUALQUIER AUTENTICACIÓN (cualquier rol logeado)
            .anyRequest().authenticated()
        )
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"
        ));
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", "Content-Type", "Accept", "Origin", 
            "X-Requested-With", "Access-Control-Request-Method", 
            "Access-Control-Request-Headers"
        ));
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization", "Content-Type", "Access-Control-Allow-Origin"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}