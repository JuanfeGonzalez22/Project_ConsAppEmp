package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un módulo dentro de un curso")
public class ModuleDTO {

    @Schema(description = "ID del módulo", example = "1")
    private Long id; // ← AGREGAR ESTE CAMPO

    @Schema(description = "ID del curso al que pertenece este módulo", example = "1001")
    private Long courseId;

    @Schema(description = "Nombre o título del módulo", example = "Introducción a la plataforma")
    private String title;

    @Schema(description = "Tipo de módulo (video, texto, cuestionario o práctica)", example = "video")
    private String type;

    @Schema(description = "Orden en el que aparece el módulo dentro del curso", example = "1")
    private int order;
}
