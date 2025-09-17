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


    @Schema(description = "ID del curso al que pertenece este módulo", example = "1001")
    private int cursoId;

    @Schema(description = "Nombre o título del módulo", example = "Introducción a la plataforma")
    private String titulo;

    @Schema(description = "Qué tipo de módulo es (video, texto, quiz o práctica)", example = "video")
    private String tipo; //video, texto, quiz y practica

    @Schema(description = "El orden en que aparece el módulo dentro del curso", example = "1")
    private int orden;

}
