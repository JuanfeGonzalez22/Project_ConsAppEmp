package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;
import com.eam.LevelUpCorp.businessLayer.service.EvaluationService;
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

@RestController
@RequestMapping("/api/v1/evaluaciones")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Evaluaciones", description = "Gestion de evaluaciones")
@CrossOrigin(origins = "*")
public class EvaluationController {


    private final EvaluationService evaluationService;


    /*
        Create an evaluation.
     */
    @PostMapping
    @Operation(summary = "Crear una evaluación", description = "Crear una nueva  evaluación en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluacion creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos validos")
    })
    public ResponseEntity<EvaluationDTO> createEvaluation(
            @Parameter(description = "Datos de la evaluacion a crear", required = true)
            @RequestBody EvaluationDTO evaluationDTO
    ) {
        log.info("POST /api/v1/evaluations - Creando evaluacionn: {}", evaluationDTO.getTitle());
        try {
            EvaluationDTO created = evaluationService.create(evaluationDTO);
            log.info("Evaluatión created con ID: {}", created);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear una evaluacion: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /*
        Get an evaluation by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener evaluacion por ID", description = "Obtener una evaluacion especifico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluacion encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada")
    })
    public ResponseEntity<EvaluationDTO> getById(
            @Parameter(description = "ID de la evaluacion", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/evaluations/{} - Buscar evaluacion", id);
        try {
            EvaluationDTO evaluation = evaluationService.getById(id);
            return ResponseEntity.ok(evaluation);
        } catch (RuntimeException e) {
            log.warn("Evaluacion no encontrada con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /*
     Get list of evaluations.
 */
    @GetMapping
    @Operation(summary = "Lista de evaluaciones", description = "Obtener todas las evaluaciones en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de evaluaciones",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class)))
    })
    public ResponseEntity<List<EvaluationDTO>> findAll() {
        log.debug("GET /api/v1/evaluations - Obtener todas las evaluaciones");
        List<EvaluationDTO> evaluations = evaluationService.findAll();
        log.debug("Found {} evaluaciones", evaluations.size());
        return ResponseEntity.ok(evaluations);
    }

    /*
        Get list of evaluations by module.
    */
    @GetMapping("/module/{moduleId}")
    @Operation(summary = "Listar evaluaciones por módulo", description = "Obtiene todas las evaluaciones asociadas a un módulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de evaluaciones del módulo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron evaluaciones")
    })
    public ResponseEntity<List<EvaluationDTO>> getByModuleId(
            @Parameter(description = "ID del módulo", required = true)
            @PathVariable Long moduleId
    ) {
        log.debug("GET /api/v1/evaluations/module/{} - Buscando evaluaciones por módulo", moduleId);
        try {
            List<EvaluationDTO> evaluations = evaluationService.getEvaluationByModuleId(moduleId);
            return ResponseEntity.ok(evaluations);
        } catch (RuntimeException e) {
            log.warn("No se encontraron evaluaciones para el módulo con ID: {}", moduleId);
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Update an evaluation.
    */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evaluación", description = "Actualiza una evaluación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<EvaluationDTO> update(
            @Parameter(description = "ID de la evaluación a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos de la evaluación a actualizar", required = true)
            @RequestBody EvaluationDTO evaluationDTO
    ) {
        log.info("PUT /api/v1/evaluations/{} - Actualizando evaluación", id);
        try {
            EvaluationDTO updated = evaluationService.update(id, evaluationDTO);
            log.info("Evaluación actualizada con ID: {}", id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar la evaluación con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /*
        Delete an evaluation.
    */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evaluación", description = "Elimina una evaluación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evaluación eliminada"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la evaluación a eliminar", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/evaluations/{} - Eliminando evaluación", id);
        try {
            evaluationService.deleteById(id);
            log.info("Evaluación eliminada con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Evaluación no encontrada para eliminar con ID: {}", id);
            return ResponseEntity.notFound().build();
        }

    }


}