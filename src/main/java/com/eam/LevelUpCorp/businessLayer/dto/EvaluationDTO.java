package com.eam.LevelUpCorp.businessLayer.dto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Este es un objeto de datos que muestra una evaluacion con moduloId, titulo, tipo y un puntaje Maximo para asignar.")
public class EvaluationDTO {


    private int moduloId;
    private String titulo;
    private String tipo;
    private int puntajeMax;


}
