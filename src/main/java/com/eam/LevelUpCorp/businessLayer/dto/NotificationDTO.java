package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents a notification sent to a user. It includes who receives it, the type, the message, when it was sent, and the current status.")
public class NotificationDTO {

    @Schema(description = "ID of the notification", example = "1")
    private Long id;

    @Schema(description = "ID of the user", example = "101")
    private Long userId;

    @Schema(description = "Type of notification", example = "PROGRESS_UPDATE")
    private String type;

    @Schema(description = "Notification message", example = "¡Curso completado!")
    private String message;

    @Schema(description = "Sent date", example = "2024-01-15")
    private LocalDate sentDate;

    @Schema(description = "Status of notification", example = "UNREAD")
    private String status;
}
