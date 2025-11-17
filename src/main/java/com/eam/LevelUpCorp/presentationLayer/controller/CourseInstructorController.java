package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.CourseInstructorService;
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
 * Controlador REST para la gestión de asignaciones de instructores a cursos
 */
@RestController
@RequestMapping("/api/v1/course-instructors")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Asignación de Instructores", description = "Gestión de asignaciones de instructores a cursos")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseInstructorController {

    private final CourseInstructorService courseInstructorService;

    /**
     * Crear una nueva asignación de instructor a un curso
     */
    @PostMapping
    @Operation(summary = "Asignar instructor a curso", description = "Crea una nueva asignación de un instructor a un curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asignación creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseInstructorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CourseInstructorResponseDTO> createAssignment(
            @Parameter(description = "Datos de la asignación", required = true)
            @RequestBody CourseInstructorDTO createDTO
    ) {
        log.info("POST /api/v1/course-instructors - Creando asignación: {}", createDTO);
        try {
            CourseInstructorResponseDTO created = courseInstructorService.createAssignment(createDTO);
            log.info("Asignación creada con ID: {}", created.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear asignación: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtener asignación por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener asignación por ID", description = "Obtiene una asignación específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseInstructorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    public ResponseEntity<CourseInstructorResponseDTO> getAssignmentById(
            @Parameter(description = "ID de la asignación", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/course-instructors/{} - Buscando asignación", id);
        try {
            CourseInstructorResponseDTO dto = courseInstructorService.getAssignmentById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            log.warn("Asignación no encontrada con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener todas las asignaciones
     */
    @GetMapping
    @Operation(summary = "Listar asignaciones", description = "Obtiene todas las asignaciones de instructores a cursos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asignaciones",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseInstructorResponseDTO.class)))
    })
    public ResponseEntity<List<CourseInstructorResponseDTO>> getAllAssignments() {
        log.debug("GET /api/v1/course-instructors - Obteniendo todas las asignaciones");
        List<CourseInstructorResponseDTO> assignments = courseInstructorService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    /**
     * Actualizar una asignación
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar asignación", description = "Actualiza una asignación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseInstructorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    public ResponseEntity<CourseInstructorResponseDTO> updateAssignment(
            @Parameter(description = "ID de la asignación", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos de la asignación a actualizar", required = true)
            @RequestBody CourseInstructorDTO updateDTO
    ) {
        log.info("PUT /api/v1/course-instructors/{} - Actualizando asignación", id);
        try {
            CourseInstructorResponseDTO updated = courseInstructorService.updateAssignment(id, updateDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar asignación ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar una asignación
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar asignación", description = "Elimina una asignación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asignación eliminada"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    public ResponseEntity<Void> deleteAssignment(
            @Parameter(description = "ID de la asignación a eliminar", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/course-instructors/{} - Eliminando asignación", id);
        try {
            courseInstructorService.deleteAssignment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Asignación no encontrada para eliminar ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
