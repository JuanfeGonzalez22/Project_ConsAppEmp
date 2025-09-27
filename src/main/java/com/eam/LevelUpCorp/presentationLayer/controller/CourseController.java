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

import java.util.List;


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
    @Operation(summary = "Create course", description = "Create a new course in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course create",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dates invalid")
    })
    public ResponseEntity<CourseDTO> createCourse(@Parameter(description = "Dates of course", required = true)
                                                      @RequestBody CourseDTO courseDTO) {
        log.info("POST /api/v1/courses - Create course: {}", courseDTO.getTitle());
        try {
            CourseDTO createdCourse = courseService.createCourse(courseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (IllegalArgumentException e) {
            log.warn("Error creating a course: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    /*
        Get a course to ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a course to ID", description = "Get a specific course by your ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseDTO> getCourseById(@Parameter(description = "ID of course", required = true)
                                                       @PathVariable Long id) {
        log.debug("GET /api/v1/courses/{} - Search course", id);
        try {
            CourseDTO course = courseService.getCourse(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            log.warn("Course not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Delete a course.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Delete a course Existing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course delete"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<Void> deleteCourse(@Parameter(description = "ID of course", required = true)
                                                 @PathVariable Long id) {
        log.info("DELETE /api/v1/courses/{} - Delete course", id);
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Course not found for delete with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Update a course.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update a course existing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course update",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dates inválid"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseDTO courseDTO
    ) {
        log.info("PUT /api/v1/courses/{} - Update course", id);
        try {
            CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            log.warn("Error updating course ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Get all courses.
     */
    @GetMapping
    @Operation(summary = "List courses", description = "Get all the courses available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of courses",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class)))
    })
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        log.debug("GET /api/v1/courses - Get all courses");
        List<CourseDTO> courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }


}