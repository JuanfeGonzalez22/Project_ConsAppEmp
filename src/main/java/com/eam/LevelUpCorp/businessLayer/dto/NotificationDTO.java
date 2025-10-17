package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa una notificación enviada a un usuario. Incluye quién la recibe, el tipo, el mensaje, cuándo fue enviada y su estado actual.")
public class NotificationDTO {

    @Schema(description = "ID de la notificación", example = "1")
    private Long id;

    @Schema(description = "ID del usuario", example = "101")
    private Long userId;

    @Schema(description = "Tipo de notificación", example = "ACTUALIZACIÓN_DE_PROGRESO")
    private String type;

    @Schema(description = "Mensaje de la notificación", example = "¡Curso completado!")
    private String message;

    @Schema(description = "Fecha en que se envió la notificación", example = "2024-01-15")
    private LocalDate sentDate;

    @Schema(description = "Estado de la notificación", example = "NO_LEÍDA")
    private String status;
}
