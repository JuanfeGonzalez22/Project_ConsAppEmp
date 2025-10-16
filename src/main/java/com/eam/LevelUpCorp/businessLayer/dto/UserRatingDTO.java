package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa la información de calificación de un usuario en la plataforma")
public class UserRatingDTO {

    @Schema(description = "Identificador único del usuario que está siendo calificado", example = "123")
    private Long userId;

    @Schema(description = "Identificador único de la calificación asignada al usuario", example = "456")
    private Long ratingId;

}

