package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.NotificationDTO;
import com.eam.LevelUpCorp.businessLayer.service.NotificationService;
import com.eam.LevelUpCorp.persistenceLayer.dao.NotificationDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {


    private final NotificationDAO notificationDAO;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUserNotifications(Long userId) {
        log.info("Getting notifications for user ID: {}", userId);
        List<NotificationDTO> notifications = notificationDAO.findUnreadByUserId(userId);

        if (notifications.isEmpty()) {
            log.info("No notifications found for user ID: {}", userId);
        } else {
            log.info("Found {} notifications for user ID: {}", notifications.size(), userId);
        }

        return notifications;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        log.info("Getting unread notifications for user ID: {}", userId);
        List<NotificationDTO> unreadNotifications = notificationDAO.findUnreadByUserId(userId);
        log.info("Found {} unread notifications for user ID: {}", unreadNotifications.size(), userId);
        return unreadNotifications;
    }

    @Override
    public NotificationDTO markAsRead(Long notificationId) {
        log.info("Marking notification as read - ID: {}", notificationId);
        NotificationDTO updatedNotification = notificationDAO.markAsRead(notificationId)
                .orElseThrow(() -> {
                    log.warn("Notification not found - ID: {}", notificationId);
                    return new RuntimeException("Notification not found with ID: " + notificationId);
                });
        log.info("Notification marked as read successfully - ID: {}", notificationId);
        return updatedNotification;
    }

    @Override
    @Transactional(readOnly = true)
    public int getUnreadCount(Long userId) {
        log.debug("Counting unread notifications for user ID: {}", userId);
        int count = notificationDAO.countUnreadByUserId(userId);
        log.debug("User ID: {} has {} unread notifications", userId, count);
        return count;
    }

    @Override
    public void createProgressNotification(Long userId, String type, String message) {
        log.info("Creating progress notification for user ID: {} - Type: {}", userId, type);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUserId(userId);
        notificationDTO.setType(type);
        notificationDTO.setMessage(message);
        notificationDTO.setStatus("UNREAD");

        NotificationDTO savedNotification = notificationDAO.save(notificationDTO);
        log.info("Progress notification created successfully - ID: {}", savedNotification.getId());
    }

}
