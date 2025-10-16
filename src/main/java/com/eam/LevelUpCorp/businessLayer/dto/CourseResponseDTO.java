package com.eam.LevelUpCorp.businessLayer.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un curso con su descripción, título, duración estimada y nivel de dificultad.")
public class CourseResponseDTO {

    @Schema(description = "Identificador del curso", example = "1")
    private Long id;

    @Schema(description = "Descripción breve del curso", example = "Curso introductorio sobre los fundamentos de Java y programación orientada a objetos.")
    private String description;

    @Schema(description = "Título del curso", example = "Fundamentos de Java")
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
