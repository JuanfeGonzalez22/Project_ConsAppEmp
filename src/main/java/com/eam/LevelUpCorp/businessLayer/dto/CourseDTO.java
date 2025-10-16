package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos que representa un curso con su descripción, título, duración estimada y nivel de dificultad.")
public class CourseDTO {

    @Schema(description = "Descripción breve del curso")
    private String description;

    @Schema(description = "Título del curso")
    private String title;

    @Schema(
            description = "Duración estimada del curso en formato HH:mm:ss",
            type = "string",
            example = "02:15:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime estimatedDuration;

    @Schema(description = "Nivel de dificultad del curso", example = "1")
    private int level;
}
