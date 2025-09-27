package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
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


@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Courses", description = "Courses management")
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
    public ResponseEntity<CourseDTO> createCourse(@Parameter(description = "Datos del usuario a crear", required = true)
                                                      @RequestBody CourseDTO courseDTO) {
        log.info("POST /api/v1/courses - Creando curso: {}", courseDTO.getTitle());
        try {
            CourseDTO createdCourse = courseService.createCourse(courseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear curso: {}", e.getMessage());
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
    public ResponseEntity<CourseDTO> getCourseById(@Parameter(description = "ID del usuario", required = true)
                                                       @PathVariable Long id) {
        log.debug("GET /api/v1/courses/{} - Buscando curso", id);
        try {
            CourseDTO course = courseService.getCourse(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            log.warn("Curso no encontrado con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

}