package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un reporte de un instructor y los cursos que tiene asignados")
public class InstructorReportDTO {

    @Schema(description = "Identificador único del instructor", example = "12")
    private Long instructorId;

    @Schema(description = "Nombre completo del instructor", example = "Erickson Jonson")
    private String instructorName;

    @Schema(description = "Lista de cursos asignados al instructor junto con sus estadísticas")
    private List<CourseSummaryDTO> assignedCourses;
}
