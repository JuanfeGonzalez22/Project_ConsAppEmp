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
                        // ==================== 🔓 RUTAS PÚBLICAS ====================
                        .requestMatchers(
                                "/project/api/v1/auth/**",
                                "/api/v1/auth/**",
                                "/project/swagger-ui/**",
                                "/project/v3/api-docs/**",
                                "/project/api-docs/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/project/error",
                                "/error"
                        ).permitAll()

                        // ==================== 👥 GESTIÓN DE USUARIOS ====================
                        .requestMatchers(
                                "/project/api/v1/users/**",
                                "/api/v1/users/**"
                        ).hasRole("ADMIN")

                        // ==================== 📚 CURSOS ====================
                        // LECTURA (GET) - TODOS LOS ROLES AUTENTICADOS
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ESCRITURA (POST, PUT, DELETE) - SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/project/api/v1/courses/**",
                                "/api/v1/courses/**"
                        ).hasRole("ADMIN")

                        // ==================== 📑 MÓDULOS ====================
                        // LECTURA (GET) - TODOS LOS ROLES AUTENTICADOS
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/modules/**",
                                "/api/v1/modules/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ESCRITURA (POST, PUT, DELETE) - SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/modules/**",
                                "/api/v1/modules/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/project/api/v1/modules/**",
                                "/api/v1/modules/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/project/api/v1/modules/**",
                                "/api/v1/modules/**"
                        ).hasRole("ADMIN")

                        // ==================== 📝 INSCRIPCIONES (REGISTRATIONS) ====================
                        // LECTURA (GET) - ADMIN E INSTRUCTOR
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/registrations/**",
                                "/api/v1/registrations/**"
                        ).hasAnyRole("ADMIN", "INSTRUCTOR")

                        // ESCRITURA (POST, PUT, DELETE) - SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/registrations/**",
                                "/api/v1/registrations/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/project/api/v1/registrations/**",
                                "/api/v1/registrations/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/project/api/v1/registrations/**",
                                "/api/v1/registrations/**"
                        ).hasRole("ADMIN")

                        // ==================== 👨‍🏫 ASIGNACIÓN DE INSTRUCTORES ====================
                        // TODAS LAS OPERACIONES - SOLO ADMIN
                        .requestMatchers(
                                "/project/api/v1/course-instructors/**",
                                "/api/v1/course-instructors/**"
                        ).hasRole("ADMIN")

                        // ==================== 🎮 GAMIFICACIÓN (RATINGS) ====================
                        // ✅ NUEVO: GESTIÓN DE GAMIFICACIÓN - SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/ratings/**",
                                "/api/v1/ratings/**"
                        ).hasAnyRole("ADMIN", "INSTRUCTOR") // Admin e instructores pueden ver

                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/ratings/**",
                                "/api/v1/ratings/**"
                        ).hasRole("ADMIN") // Solo admin puede crear

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/project/api/v1/ratings/**",
                                "/api/v1/ratings/**"
                        ).hasRole("ADMIN") // Solo admin puede actualizar

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/project/api/v1/ratings/**",
                                "/api/v1/ratings/**"
                        ).hasRole("ADMIN") // Solo admin puede eliminar

                        // ✅ NUEVO: LOGROS DE USUARIOS - MULTIPLES ROLES
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/ratings/users/**",
                                "/api/v1/ratings/users/**"
                        ).hasAnyRole("ADMIN", "INSTRUCTOR", "APRENDIZ") // Todos pueden ver sus logros

                        // ==================== 🧪 EVALUACIONES ====================
                        // LECTURA (GET) - TODOS LOS ROLES AUTENTICADOS
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ESCRITURA (POST, PUT, DELETE) - SOLO ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/project/api/v1/evaluaciones/**",
                                "/api/v1/evaluaciones/**"
                        ).hasRole("ADMIN")

                        // ==================== 🎯 INTENTOS DE EVALUACIÓN ====================
                        // REALIZAR EVALUACIONES (POST) - ADMIN Y APRENDIZ
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/evaluation-attempts/**",
                                "/api/v1/evaluation-attempts/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ")

                        // VER RESULTADOS (GET) - TODOS LOS ROLES AUTENTICADOS
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/evaluation-attempts/**",
                                "/api/v1/evaluation-attempts/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ==================== 📚 MATERIALES DEL CURSO ====================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/materials/**",
                                "/api/v1/materials/**",
                                "/project/api/v1/course-materials/**",
                                "/api/v1/course-materials/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ==================== ❓ QUIZZES ====================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/quizzes/**",
                                "/api/v1/quizzes/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // REALIZAR QUIZZES (POST) - ADMIN Y APRENDIZ
                        .requestMatchers(
                                HttpMethod.POST,
                                "/project/api/v1/quiz-attempts/**",
                                "/api/v1/quiz-attempts/**",
                                "/project/api/v1/quiz-submissions/**",
                                "/api/v1/quiz-submissions/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ")

                        // ==================== 📊 PROGRESO DEL ESTUDIANTE ====================
                        .requestMatchers(
                                "/project/api/v1/progress/**",
                                "/api/v1/progress/**",
                                "/project/api/v1/student-progress/**",
                                "/api/v1/student-progress/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ==================== 🏆 CERTIFICADOS ====================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/project/api/v1/certificates/**",
                                "/api/v1/certificates/**"
                        ).hasAnyRole("ADMIN", "APRENDIZ", "INSTRUCTOR")

                        // ==================== 📈 REPORTES ====================
                        .requestMatchers(
                                "/project/api/v1/reports/**",
                                "/api/v1/reports/**"
                        ).hasAnyRole("ADMIN", "INSTRUCTOR")

                        // ==================== 👨‍🏫 RUTAS DE INSTRUCTOR ====================
                        .requestMatchers(
                                "/project/api/v1/instructor/**",
                                "/api/v1/instructor/**"
                        ).hasRole("INSTRUCTOR")

                        // ==================== 👨‍🎓 RUTAS DE APRENDIZ ====================
                        .requestMatchers(
                                "/project/api/v1/apprentice/**",
                                "/api/v1/apprentice/**",
                                "/project/api/v1/student/**",
                                "/api/v1/student/**"
                        ).hasRole("APRENDIZ")

                        // ==================== 🔒 EL RESTO REQUIERE AUTENTICACIÓN ====================
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