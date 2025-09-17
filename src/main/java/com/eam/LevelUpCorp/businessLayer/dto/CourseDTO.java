package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Este es un objeto de datos que muestra un curso con descripcion, titulo, duracion Estimada y un nivel para asignar.")
public class CourseDTO {

    private String descripcion;
    private String titulo;
    private LocalTime duracionEstimada;
    private int nivel;
}
