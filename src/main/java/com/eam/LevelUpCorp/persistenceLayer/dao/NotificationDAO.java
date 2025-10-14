package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.NotificationDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.NotificationEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.NotificationMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationDAO {


    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


    //Save
    public NotificationDTO save(NotificationDTO notificationDTO) {
        NotificationEntity notificationEntity = notificationMapper.toEntity(notificationDTO);
        NotificationEntity savedEntity = notificationRepository.save(notificationEntity);
        return notificationMapper.toDTO(savedEntity);
    }


    //Find by ID
    public Optional<NotificationDTO> findByUserId(Long id) {
        return notificationRepository.findById(id)
                .map(notificationMapper::toDTO);
    }




    // Get unread notifications for user
    public List<NotificationDTO> findUnreadByUserId(Long userId) {
        List<NotificationEntity> entities = notificationRepository.findByUserIdAndStatusOrderBySentDateDesc(userId, "UNREAD");
        return notificationMapper.toDTOList(entities);
    }

    // Count unread notifications
    public int countUnreadByUserId(Long userId) {
        return notificationRepository.countByUserIdAndStatus(userId, "UNREAD");
    }

    // Mark notification as read
    public Optional<NotificationDTO> markAsRead(Long id) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.setStatus("READ");
                    NotificationEntity updatedEntity = notificationRepository.save(notification);
                    return notificationMapper.toDTO(updatedEntity);
                });
    }

    // Delete notification
    public boolean deleteById(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
