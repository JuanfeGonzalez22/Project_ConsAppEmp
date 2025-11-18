package com.eam.LevelUpCorp.configJWT;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "LevelUpCorp API",
                version = "1.0.0",
                description = "API REST para la gestión de cursos, instructores y aprendices en la plataforma de capacitación LevelUpCorp. Permite registro de usuarios, asignación de cursos, generación de reportes y seguimiento del progreso de los estudiantes.",
                contact = @Contact(
                        name = "Equipo LevelUpCorp",
                        email = "dev@levelupcorp.com",
                        url = "levelupcorp.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor de Desarrollo"
                )
        }
)
public class OpenApiConfig {

//    @Bean
//    public OpenAPI customOpenAPI() {
        //return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("Bearer Authentication",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")
//                                        .description("Ingresa tu token JWT")
//                        )
//                );
    //}
}

