package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa la información básica de una calificación de usuario en la plataforma")
public class UserRatingResponseDTO {

    @Schema(description = "Identificador único del registro de calificación", example = "1")
    private Long id;

    @Schema(description = "ID del usuario que está siendo calificado", example = "123")
    private Long userId;

    @Schema(description = "ID de la calificación específica", example = "456")
    private Long ratingId;

    @Schema(description = "Fecha en la que se otorgó la calificación", example = "2024-01-15")
    private LocalDate date;


}
