package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    //Obtener notificaciones del usuario
    List<NotificationDTO> getUserNotifications(Long userId);

    // Obtener notificaciones no leídas
    List<NotificationDTO> getUnreadNotifications(Long userId);

    // Marcar notificación como leída
    NotificationDTO markAsRead(Long notificationId);

    // Contar notificaciones no leídas
    int getUnreadCount(Long userId);

    //Crear notificacion automatica
    void createProgressNotification(Long userId, String type, String message);







}
