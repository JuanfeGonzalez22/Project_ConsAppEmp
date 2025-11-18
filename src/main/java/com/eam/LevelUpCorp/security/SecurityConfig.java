package com.eam.LevelUpCorp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        // ✅ RUTAS PÚBLICAS (todos pueden acceder sin autenticación)
                        .requestMatchers(
                                "/project/api/v1/auth/**",
                                "/project/swagger-ui/**",
                                "/project/v3/api-docs/**",
                                "/project/api-docs/**",
                                "/project/error",
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

                        // ✅ CURSOS - LECTURA (GET) PARA TODOS LOS ROLES AUTENTICADOS
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ CURSOS - CREAR (POST) SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasRole("ADMIN")

                        // ✅ CURSOS - ACTUALIZAR (PUT) SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasRole("ADMIN")

                        // ✅ CURSOS - ELIMINAR (DELETE) SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasRole("ADMIN")

                        // ✅ MÓDULOS - LECTURA (GET) PARA TODOS LOS ROLES AUTENTICADOS
                        .requestMatchers(HttpMethod.GET, "/project/api/v1/modules/**").hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/modules/**").hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ MÓDULOS - CREAR (POST) SOLO ADMIN
                        .requestMatchers(HttpMethod.POST, "/project/api/v1/modules/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/modules/**").hasRole("ADMIN")

                        // ✅ MÓDULOS - ACTUALIZAR (PUT) SOLO ADMIN
                        .requestMatchers(HttpMethod.PUT, "/project/api/v1/modules/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/modules/**").hasRole("ADMIN")

                        // ✅ MÓDULOS - ELIMINAR (DELETE) SOLO ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/project/api/v1/modules/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/modules/**").hasRole("ADMIN")

                        // ✅ EVALUACIONES - LECTURA (GET) PARA TODOS LOS ROLES AUTENTICADOS
                        // APRENDIZ puede ver evaluaciones disponibles
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ EVALUACIONES - CREAR (POST) SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasRole("ADMIN")

                        // ✅ EVALUACIONES - ACTUALIZAR (PUT) SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasRole("ADMIN")

                        // ✅ EVALUACIONES - ELIMINAR (DELETE) SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasRole("ADMIN")

                        // ✅ INTENTOS DE EVALUACIÓN - APRENDIZ puede realizar evaluaciones
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/evaluation-attempts/**",
                                "/api/v1/evaluation-attempts/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ")

                        // ✅ INTENTOS DE EVALUACIÓN - LECTURA (para ver resultados)
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/evaluation-attempts/**",
                                "/api/v1/evaluation-attempts/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ MATERIALES DEL CURSO - LECTURA PARA APRENDIZ
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/materials/**",
                                "/api/v1/materials/**",
                                "/project/api/v1/course-materials/**",
                                "/api/v1/course-materials/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ QUIZZES (si tienes endpoints separados de evaluaciones)
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/quizzes/**",
                                "/api/v1/quizzes/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ QUIZ ATTEMPTS - REALIZAR QUIZ (POST) PARA APRENDIZ
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/quiz-attempts/**",
                                "/api/v1/quiz-attempts/**",
                                "/project/api/v1/quiz-submissions/**",
                                "/api/v1/quiz-submissions/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ")

                        // ✅ PROGRESO DEL ESTUDIANTE - LECTURA Y ESCRITURA PARA APRENDIZ
                        .requestMatchers(
                                "/project/api/v1/progress/**",
                                "/api/v1/progress/**",
                                "/project/api/v1/student-progress/**",
                                "/api/v1/student-progress/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ CERTIFICADOS - LECTURA PARA APRENDIZ
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/certificates/**",
                                "/api/v1/certificates/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ✅ RUTAS DE INSTRUCTOR
                        .requestMatchers(
                                "/project/api/v1/instructor/**",
                                "/api/v1/instructor/**"
                        ).hasRole("INSTRUCTOR")

                        // ✅ RUTAS ESPECÍFICAS DE APRENDIZ (dashboard, perfil, etc.)
                        .requestMatchers(
                                "/project/api/v1/apprentice/**",
                                "/api/v1/apprentice/**",
                                "/project/api/v1/student/**",
                                "/api/v1/student/**"
                        ).hasRole("APRENDIZ")

                        // ✅ EL RESTO REQUIERE CUALQUIER AUTENTICACIÓN
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