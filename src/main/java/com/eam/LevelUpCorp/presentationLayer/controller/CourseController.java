package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.CourseService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Cursos", description = "Gestión de cursos")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    /*
        Create a course.
     */
    @PostMapping
    @Operation(summary = "Crear curso", description = "Crea un nuevo curso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CourseResponseDTO> createCourse(
            @Parameter(description = "Datos del curso", required = true)
            @RequestBody CourseDTO courseDTO
    ) {
        log.info("POST /api/v1/courses - Crear curso: {}", courseDTO.getTitle());
        try {
            CourseResponseDTO createdCourse = courseService.createCourse(courseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear el curso: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /*
        Get a course to ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener curso por ID", description = "Obtiene un curso específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<CourseResponseDTO> getCourseById(
            @Parameter(description = "ID del curso", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/courses/{} - Buscando curso", id);
        try {
            CourseResponseDTO course = courseService.getCourse(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            log.warn("Curso no encontrado con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Delete a course.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso", description = "Elimina un curso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso eliminado"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "ID del curso", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/courses/{} - Eliminando curso", id);
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Curso no encontrado para eliminar con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Update a course.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar curso", description = "Actualiza un curso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseDTO courseDTO
    ) {
        log.info("PUT /api/v1/courses/{} - Actualizando curso", id);
        try {
            CourseResponseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar el curso con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Get all courses.
     */
    @GetMapping
    @Operation(summary = "Listar cursos", description = "Obtiene todos los cursos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class)))
    })
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        log.debug("GET /api/v1/courses - Obteniendo todos los cursos");
        List<CourseResponseDTO> courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

}
