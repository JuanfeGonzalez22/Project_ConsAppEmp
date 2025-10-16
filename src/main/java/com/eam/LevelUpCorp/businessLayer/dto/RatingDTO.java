package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Schema(description = "DTO que representa una insignia o recompensa dentro de la plataforma")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    @Schema(description = "Nombre de la recompensa", example = "Mejor desempeño")
    private String name;

    @Schema(description = "Condición requerida para obtener la recompensa", example = "Completar 3 cursos")
    private String criterion;

    @Schema(description = "Ruta o nombre del archivo del ícono de la insignia", example = "medal.png")
    private String icono;

    @Schema(description = "Código único de la recompensa", example = "RWD-001")
    private String code;
}
