package com.eam.LevelUpCorp.businessLayer.dto;

public class GamificationDTO {
    @Schema(description = "Identificador del badge o recompensa", example = "1")
    private Long id;

    @Schema(description = "Nombre de la recompensa", example = "Top performer")
    private String nombre;

    @Schema(description = "Condición para obtener la recompensa", example = "Completar 3 cursos")
    private String criterio;

    @Schema(description = "Ruta o nombre del icono del badge", example = "medalla.png")
    private String icono;
}
