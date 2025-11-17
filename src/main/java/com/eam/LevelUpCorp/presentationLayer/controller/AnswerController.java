package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.AnswerResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.GradeAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.dto.SubmitAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.service.AnswerService;
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

import java.util.List;

/**
 * Controlador REST para operaciones de respuestas a evaluaciones
 */
@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Respuestas", description = "Gestión de respuestas a evaluaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class AnswerController {

    private final AnswerService answerService;

    /**
     * Enviar respuesta a evaluación (Estudiante)
     */
    @PostMapping
    @Operation(summary = "Enviar respuesta", description = "El estudiante envía su respuesta a una evaluación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Respuesta enviada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<AnswerResponseDTO> submitAnswer(
            @Parameter(description = "Datos de la respuesta a enviar", required = true)
            @RequestBody SubmitAnswerDTO submitAnswerDTO,
            @Parameter(description = "ID del usuario", required = true)
            @RequestHeader Long userId,
            @Parameter(description = "ID del archivo de respuesta", required = true)
            @RequestHeader Long fileId
    ) {
        log.info("POST /api/v1/answers - Usuario {} enviando respuesta a evaluación {}", userId, submitAnswerDTO.getEvaluationId());
        try {
            AnswerResponseDTO createdAnswer = answerService.submitAnswer(submitAnswerDTO, userId, fileId);
            log.info("Respuesta enviada con ID: {}", createdAnswer.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
        } catch (IllegalArgumentException e) {
            log.warn("Error al enviar respuesta: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Calificar respuesta (Instructor)
     */
    @PutMapping("/{id}/grade")
    @Operation(summary = "Calificar respuesta", description = "El instructor califica una respuesta enviada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuesta calificada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
    })
    public ResponseEntity<AnswerResponseDTO> gradeAnswer(
            @Parameter(description = "ID de la respuesta a calificar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos de calificación", required = true)
            @RequestBody GradeAnswerDTO gradeAnswerDTO
    ) {
        log.info("PUT /api/v1/answers/{}/grade - Calificando respuesta", id);
        try {
            AnswerResponseDTO gradedAnswer = answerService.gradeAnswer(id, gradeAnswerDTO);
            log.info("Respuesta calificada con puntaje: {}", gradedAnswer.getScore());
            return ResponseEntity.ok(gradedAnswer);
        } catch (RuntimeException e) {
            log.warn("Error al calificar respuesta ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener respuesta por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener respuesta por ID", description = "Obtiene una respuesta específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuesta encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
    })
    public ResponseEntity<AnswerResponseDTO> getAnswerById(
            @Parameter(description = "ID de la respuesta", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/answers/{} - Buscando respuesta", id);
        try {
            AnswerResponseDTO answer = answerService.getAnswerById(id);
            return ResponseEntity.ok(answer);
        } catch (RuntimeException e) {
            log.warn("Respuesta no encontrada con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener todas las respuestas
     */
    @GetMapping
    @Operation(summary = "Listar respuestas", description = "Obtiene todas las respuestas del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de respuestas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponseDTO.class)))
    })
    public ResponseEntity<List<AnswerResponseDTO>> getAllAnswers() {
        log.debug("GET /api/v1/answers - Obteniendo todas las respuestas");
        List<AnswerResponseDTO> answers = answerService.getAllAnswers();
        log.debug("Se encontraron {} respuestas", answers.size());
        return ResponseEntity.ok(answers);
    }

    /**
     * Obtener respuestas por evaluación (Instructor)
     */
    @GetMapping("/evaluation/{evaluationId}")
    @Operation(summary = "Respuestas por evaluación", description = "Obtiene todas las respuestas de una evaluación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de respuestas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron respuestas")
    })
    public ResponseEntity<List<AnswerResponseDTO>> getAnswersByEvaluation(
            @Parameter(description = "ID de la evaluación", required = true)
            @PathVariable Long evaluationId
    ) {
        log.debug("GET /api/v1/answers/evaluation/{} - Buscando respuestas por evaluación", evaluationId);
        try {
            List<AnswerResponseDTO> answers = answerService.getAnswersByEvaluation(evaluationId);
            log.debug("Se encontraron {} respuestas para evaluación {}", answers.size(), evaluationId);
            return ResponseEntity.ok(answers);
        } catch (RuntimeException e) {
            log.warn("No se encontraron respuestas para evaluación: {}", evaluationId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener respuestas por usuario (Estudiante)
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Respuestas por usuario", description = "Obtiene todas las respuestas de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de respuestas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron respuestas")
    })
    public ResponseEntity<List<AnswerResponseDTO>> getAnswersByUser(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long userId
    ) {
        log.debug("GET /api/v1/answers/user/{} - Buscando respuestas por usuario", userId);
        try {
            List<AnswerResponseDTO> answers = answerService.getAnswersByUser(userId);
            log.debug("Se encontraron {} respuestas para usuario {}", answers.size(), userId);
            return ResponseEntity.ok(answers);
        } catch (RuntimeException e) {
            log.warn("No se encontraron respuestas para usuario: {}", userId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Verificar si usuario ya respondió una evaluación
     */
    @GetMapping("/check/{evaluationId}/user/{userId}")
    @Operation(summary = "Verificar respuesta", description = "Verifica si un usuario ya respondió una evaluación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación exitosa")
    })
    public ResponseEntity<Boolean> hasUserAnsweredEvaluation(
            @Parameter(description = "ID de la evaluación", required = true)
            @PathVariable Long evaluationId,
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long userId
    ) {
        log.debug("GET /api/v1/answers/check/{}/user/{} - Verificando respuesta", evaluationId, userId);
        boolean hasAnswered = answerService.hasUserAnsweredEvaluation(evaluationId, userId);
        log.debug("Usuario {} respondió evaluación {}: {}", userId, evaluationId, hasAnswered);
        return ResponseEntity.ok(hasAnswered);
    }

    /**
     * Eliminar respuesta
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar respuesta", description = "Elimina una respuesta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Respuesta eliminada"),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
    })
    public ResponseEntity<Void> deleteAnswer(
            @Parameter(description = "ID de la respuesta a eliminar", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/answers/{} - Eliminando respuesta", id);
        try {
            answerService.deleteAnswer(id);
            log.info("Respuesta eliminada con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Respuesta no encontrada para eliminar ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}