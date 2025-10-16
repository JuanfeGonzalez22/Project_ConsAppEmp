package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar un nuevo usuario en la plataforma")
public class RegistrationDTO {

    @Schema(description = "ID del usuario a inscribir", example = "12")
    private Long userId;

    @Schema(description = "ID del curso en el que se inscribirá", example = "5")
    private Long courseId;

    @Schema(description = "Estado de la inscripción (ACTIVO, COMPLETADO, RETIRADO)", example = "ACTIVO")
    private String status = "ACTIVO";
}
