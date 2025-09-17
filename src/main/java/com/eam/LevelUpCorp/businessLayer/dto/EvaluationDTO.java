package com.eam.LevelUpCorp.businessLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Este es un objeto de datos que muestra una evaluacion con moduloId, titulo, tipo y un puntaje Maximo para asignar.")
public class EvaluationDTO {

    @Schema(description = "ID del modulo dentro del curso", example = "001")
    private int moduloId;

    @Schema(description = "Titulo la evaluacion en la plataforma", example = "Evaluacion final")
    private String titulo;

    @Schema(description = "Representa el tipo de evaluacion", example = "quiz")
    private String tipo;

    @Schema(description = "Representa el punataje maximo que se puede obtener en la evaluacion", example = "10")
    private int puntajeMax;


}
