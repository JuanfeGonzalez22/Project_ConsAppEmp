package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Schema(description = "Este DTO muestra un badge o recompensa dentro de la plataforma")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
