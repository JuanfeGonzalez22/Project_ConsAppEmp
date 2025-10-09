package com.eam.LevelUpCorp.presentationLayer.controller;


import com.eam.LevelUpCorp.businessLayer.dto.RatingDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RatingResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * Controlador REST para operaciones CRUD de gamificacion
 */
@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Gamificacion", description = "Gestión de sistema de gamificación")
@CrossOrigin(origins = "*")
public class RatingController {

    private final RatingService ratingService;

    /**
     * Crear una nueva gamificacion
     */
    @PostMapping
    @Operation(summary = "Crear gamificacion", description = "Crea un nuevo elemento de gamificación en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gamificacion creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<RatingResponseDTO> createRating(
            @Parameter(description = "Datos de la gamificacion a crear", required = true)
            @RequestBody RatingDTO ratingDTO
    ) {
        log.info("POST /api/v1/ratings - Creando gamificacion: {}", ratingDTO.getName());
        try {
            RatingResponseDTO createdRating = ratingService.create(ratingDTO);
            log.info("Gamificacion creada con ID: {}", createdRating.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear gamificacion: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtener gamificacion por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener gamificacion por ID", description = "Obtiene una gamificacion específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gamificacion encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Gamificacion no encontrada")
    })
    public ResponseEntity<RatingResponseDTO> getRatingById(
            @Parameter(description = "ID de la gamificacion", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/ratings/{} - Buscando gamificacion", id);
        try {
            RatingResponseDTO rating = ratingService.getRatingById(id);
            return ResponseEntity.ok(rating);
        } catch (RuntimeException e) {
            log.warn("Gamificacion no encontrada con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener gamificacion por código
     */
    @GetMapping("/code/{code}")
    @Operation(summary = "Obtener gamificacion por código", description = "Obtiene una gamificacion específica por su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gamificacion encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Gamificacion no encontrada")
    })
    public ResponseEntity<RatingResponseDTO> getRatingByCode(
            @Parameter(description = "Código de la gamificacion", required = true)
            @PathVariable String code
    ) {
        log.debug("GET /api/v1/ratings/code/{} - Buscando gamificacion por código", code);
        try {
            RatingResponseDTO rating = ratingService.getRatingByCode(code);
            return ResponseEntity.ok(rating);
        } catch (RuntimeException e) {
            log.warn("Gamificacion no encontrada con código: {}", code);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener todas las gamificaciones
     */
    @GetMapping
    @Operation(summary = "Listar gamificaciones", description = "Obtiene todas las gamificaciones del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de gamificaciones",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponseDTO.class)))
    })
    public ResponseEntity<List<RatingResponseDTO>> getAllRatings() {
        log.debug("GET /api/v1/ratings - Obteniendo todas las gamificaciones");
        List<RatingResponseDTO> ratings = ratingService.getAllRatings();
        log.debug("Se encontraron {} gamificaciones", ratings.size());
        return ResponseEntity.ok(ratings);
    }

    /**
     * Actualizar gamificacion
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar gamificacion", description = "Actualiza una gamificacion existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gamificacion actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Gamificacion no encontrada")
    })
    public ResponseEntity<RatingResponseDTO> updateRating(
            @Parameter(description = "ID de la gamificacion a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos de la gamificacion a actualizar", required = true)
            @RequestBody RatingDTO ratingDTO
    ) {
        log.info("PUT /api/v1/ratings/{} - Actualizando gamificacion", id);
        try {
            RatingResponseDTO updatedRating = ratingService.updateRating(id, ratingDTO);
            log.info("Gamificacion actualizada con ID: {}", id);
            return ResponseEntity.ok(updatedRating);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar gamificacion ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar gamificacion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar gamificacion", description = "Elimina una gamificacion existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Gamificacion eliminada"),
            @ApiResponse(responseCode = "404", description = "Gamificacion no encontrada")
    })
    public ResponseEntity<Void> deleteRating(
            @Parameter(description = "ID de la gamificacion a eliminar", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/ratings/{} - Eliminando gamificacion", id);
        try {
            ratingService.deleteRating(id);
            log.info("Gamificacion eliminada con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Gamificacion no encontrada para eliminar ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener gamificaciones por nombre
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar gamificaciones por nombre", description = "Busca gamificaciones por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de gamificaciones encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron gamificaciones")
    })
    public ResponseEntity<List<RatingResponseDTO>> getRatingsByName(
            @Parameter(description = "Nombre a buscar", required = true)
            @RequestParam String name
    ) {
        log.debug("GET /api/v1/ratings/search?name={} - Buscando gamificaciones por nombre", name);
        try {
            List<RatingResponseDTO> ratings = ratingService.getRatingsByName(name);
            log.debug("Se encontraron {} gamificaciones con nombre: {}", ratings.size(), name);
            return ResponseEntity.ok(ratings);
        } catch (RuntimeException e) {
            log.warn("No se encontraron gamificaciones con nombre: {}", name);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Verificar si código existe
     */
    @GetMapping("/exists/{code}")
    @Operation(summary = "Verificar código", description = "Verifica si un código de gamificacion existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación exitosa")
    })
    public ResponseEntity<Boolean> existsByCode(
            @Parameter(description = "Código a verificar", required = true)
            @PathVariable String code
    ) {
        log.debug("GET /api/v1/ratings/exists/{} - Verificando código", code);
        boolean exists = ratingService.existsByCode(code);
        log.debug("Código {} existe: {}", code, exists);
        return ResponseEntity.ok(exists);
    }



    /**
     * Obtener logros de un usuario
     */
    @GetMapping("/users/{userId}/achievements")
    @Operation(summary = "Obtener logros de usuario", description = "Obtiene todos los logros de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de logros del usuario",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<RatingResponseDTO>> getLogrosUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long userId
    ) {
        log.debug("GET /api/v1/ratings/users/{}/achievements - Obteniendo logros del usuario", userId);
        try {
            List<RatingResponseDTO> logros = ratingService.getLogrosUsuario(userId);
            log.debug("Usuario {} tiene {} logros", userId, logros.size());
            return ResponseEntity.ok(logros);
        } catch (RuntimeException e) {
            log.warn("Error al obtener logros del usuario {}: {}", userId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Obtener tipos de logros disponibles
     */
    @GetMapping("/tipos-disponibles")
    @Operation(summary = "Tipos de logros disponibles", description = "Obtiene los tipos de logros que el sistema puede asignar automáticamente")
    public ResponseEntity<List<String>> getTiposLogrosDisponibles() {
        log.debug("GET /api/v1/ratings/tipos-disponibles - Obteniendo tipos de logros");
        List<String> tipos = Arrays.asList(
                "PRIMERA_RESPUESTA - Primera respuesta enviada",
                "RESPONDEDOR_NOVATO - 5 respuestas enviadas",
                "RESPONDEDOR_ACTIVO - 10 respuestas enviadas",
                "RESPONDEDOR_EXPERTO - 25 respuestas enviadas",
                "EXPERTO_RESPUESTAS - 50 respuestas enviadas",
                "MAESTRO_RESPUESTAS - 100 respuestas enviadas",
                "BUEN_ESTUDIANTE - Calificación >= 80%",
                "EXCELENTE_CALIFICACION - Calificación >= 90%",
                "SOBRESALIENTE - Calificación >= 95%",
                "PERFECTO_EVALUACION - Calificación 100%",
                "GENIO_CALIFICACIONES - 5 evaluaciones con 95%+"
        );
        return ResponseEntity.ok(tipos);
    }


}