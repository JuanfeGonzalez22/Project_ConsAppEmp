package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para crear un nuevo reporte en la plataforma")
public class ReportDTO {

    @Schema(description = "ID del usuario asociado al reporte", example = "5")
    private Long userId;

    @Schema(description = "ID del curso asociado al reporte", example = "12")
    private Long courseId;

    @Schema(description = "Título del reporte", example = "Progreso mensual del curso")
    private String title;

    @Schema(description = "Descripción detallada del reporte", example = "Este reporte muestra el progreso del estudiante durante septiembre")
    private String description;
}
