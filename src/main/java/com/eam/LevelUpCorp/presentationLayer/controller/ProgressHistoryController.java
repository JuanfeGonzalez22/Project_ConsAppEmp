package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.ProgressHistoryDTO;
import com.eam.LevelUpCorp.businessLayer.service.ProgressHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

/**
 * Controlador REST para el seguimiento de progreso de usuarios
 */
@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Progreso", description = "Seguimiento de progreso de usuarios en cursos")
@CrossOrigin(origins = "*")
public class ProgressHistoryController {

    private final ProgressHistoryService progressService;

    /**
     * Marcar módulo como completado
     */
    @PostMapping("/modules/{moduleId}/mark-completed")
    @Operation(summary = "Marcar módulo como completado", description = "Marca un módulo específico como completado por un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Módulo marcado como completado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgressHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    public ResponseEntity<ProgressHistoryDTO> markModuleAsCompleted(
            @Parameter(description = "ID del módulo", required = true, example = "1")
            @PathVariable Long moduleId,
            @Parameter(description = "ID de la inscripción", required = true, example = "1")
            @RequestParam Long registrationId,
            @Parameter(description = "Tiempo dedicado (HH:MM)", example = "00:45")
            @RequestParam(defaultValue = "00:30") String timeDedicated
    ) {
        log.info("POST /api/v1/progress/modules/{}/mark-completed - Registration: {}", moduleId, registrationId);
        try {
            LocalTime time = LocalTime.parse(timeDedicated);
            ProgressHistoryDTO progress = progressService.markModuleAsCompleted(registrationId, moduleId, time);
            log.info("Módulo {} marcado como completado - Progreso: {}%", moduleId, progress.getModuleProgress());
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            log.warn("Error al marcar módulo como completado: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.warn("Error de formato en tiempo dedicado: {}", timeDedicated);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtener progreso actual
     */
    @GetMapping("/current/{registrationId}")
    @Operation(summary = "Obtener progreso actual", description = "Obtiene el progreso actual de un usuario en un curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progreso actual encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgressHistoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    public ResponseEntity<ProgressHistoryDTO> getCurrentProgress(
            @Parameter(description = "ID de la inscripción", required = true, example = "1")
            @PathVariable Long registrationId
    ) {
        log.debug("GET /api/v1/progress/current/{} - Obteniendo progreso actual", registrationId);
        try {
            ProgressHistoryDTO progress = progressService.getCurrentProgress(registrationId);
            log.debug("Progreso actual obtenido - User: {}, Progress: {}%",
                    progress.getUserId(), progress.getModuleProgress());
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            log.warn("Progreso no encontrado para inscripción: {}", registrationId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener historial de progreso
     */
    @GetMapping("/history/{registrationId}")
    @Operation(summary = "Obtener historial de progreso", description = "Obtiene el historial completo de progreso de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial de progreso encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgressHistoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    public ResponseEntity<List<ProgressHistoryDTO>> getProgressHistory(
            @Parameter(description = "ID de la inscripción", required = true, example = "1")
            @PathVariable Long registrationId
    ) {
        log.debug("GET /api/v1/progress/history/{} - Obteniendo historial de progreso", registrationId);
        try {
            List<ProgressHistoryDTO> history = progressService.getProgressHistory(registrationId);
            log.debug("Se encontraron {} registros de progreso", history.size());
            return ResponseEntity.ok(history);
        } catch (RuntimeException e) {
            log.warn("Historial no encontrado para inscripción: {}", registrationId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Verificar si módulo está completado
     */
    @GetMapping("/check-module")
    @Operation(summary = "Verificar módulo completado", description = "Verifica si un módulo específico está completado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación completada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    public ResponseEntity<Boolean> isModuleCompleted(
            @Parameter(description = "ID de la inscripción", required = true, example = "1")
            @RequestParam Long registrationId,
            @Parameter(description = "ID del módulo", required = true, example = "1")
            @RequestParam Long moduleId
    ) {
        log.debug("GET /api/v1/progress/check-module - Registration: {}, Module: {}", registrationId, moduleId);
        try {
            boolean isCompleted = progressService.isModuleCompleted(registrationId, moduleId);
            log.debug("Módulo {} completado: {}", moduleId, isCompleted);
            return ResponseEntity.ok(isCompleted);
        } catch (RuntimeException e) {
            log.warn("Error al verificar módulo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}