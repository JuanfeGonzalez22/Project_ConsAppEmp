package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Este es un objeto de datos que muestra un curso con descripcion, titulo, duracion Estimada y un nivel para asignar.")
public class CourseDTO {

    @Schema(description = "Descripcion corta del curso")
    private String descripcion;

    @Schema(description = "Representa un curso en la plataforma")
    private String titulo;

    @Schema(description = "Representa la de duracion del curso estimada en horas")
    private LocalTime duracionEstimada;

    @Schema(description = "Representa el nivel de dificultad del curso", example = "basico")
    private int nivel;
}
