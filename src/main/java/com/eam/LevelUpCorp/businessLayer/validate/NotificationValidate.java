package com.eam.LevelUpCorp.businessLayer.validate;


import com.eam.LevelUpCorp.businessLayer.dto.NotificationDTO;
import org.springframework.stereotype.Component;


@Component
public class NotificationValidate {


    public void validateCreate(NotificationDTO notificationDTO) {
        if (notificationDTO == null) {
            throw new IllegalArgumentException("La notificación no puede ser nula");
        }
        if (notificationDTO.getUserId() == null || notificationDTO.getUserId() <= 0) {
            throw new IllegalArgumentException("User ID es obligatorio y debe ser mayor a cero");
        }
        if (notificationDTO.getType() == null || notificationDTO.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de notificación es obligatorio");
        }
        if (notificationDTO.getMessage() == null || notificationDTO.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de notificación es obligatorio");
        }
        if (notificationDTO.getType().length() > 50) {
            throw new IllegalArgumentException("El tipo de notificación no puede exceder 50 caracteres");
        }
        if (notificationDTO.getMessage().length() > 500) {
            throw new IllegalArgumentException("El mensaje de notificación no puede exceder 500 caracteres");
        }
    }


    public void validateUserSearch(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID es obligatorio y debe ser mayor a cero");
        }
    }


    public void validateNotificationId(Long notificationId) {
        if (notificationId == null || notificationId <= 0) {
            throw new IllegalArgumentException("Notification ID es obligatorio y debe ser mayor a cero");
        }
    }


    public void validateProgressNotification(Long userId, String type, String message) {
        validateUserSearch(userId);

        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de notificación es obligatorio");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de notificación es obligatorio");
        }
        if (type.length() > 50) {
            throw new IllegalArgumentException("El tipo de notificación no puede exceder 50 caracteres");
        }
        if (message.length() > 500) {
            throw new IllegalArgumentException("El mensaje de notificación no puede exceder 500 caracteres");
        }
    }


    public void validateUpdate(Long notificationId, NotificationDTO notificationDTO) {
        validateNotificationId(notificationId);
        validateCreate(notificationDTO);
    }
}