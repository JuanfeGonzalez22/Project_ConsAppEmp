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

    @Schema(description = "ID of the user who receives the notification", example = "101")
    private int userId;

    @Schema(description = "Type of notification", example = "REMINDER")
    private String type;

    @Schema(description = "The content of the notification message", example = "Your course starts tomorrow!")
    private String message;

    @Schema(description = "Date when the notification was sent", example = "2025-09-20")
    private LocalDate sentDate;

    @Schema(description = "Current status of the notification", example = "READ")
    private String status;
}
