package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa al instructor asignado a un curso")
public class CourseInstructorResponseDTO {

    @Schema(description = "Identificador de la asignación del instructor al curso", example = "1")
    private Long id;

    @Schema(description = "Identificador del curso", example = "5")
    private Long courseId;

    @Schema(description = "Nombre del curso", example = "Programación en Java")
    private String courseName;

    @Schema(description = "Identificador del instructor", example = "2")
    private Long instructorId;

    @Schema(description = "Nombre del instructor", example = "John Smith")
    private String instructorName;

    @Schema(description = "Fecha de asignación", example = "2025-10-03")
    private LocalDate assignedAt = LocalDate.now();
}
