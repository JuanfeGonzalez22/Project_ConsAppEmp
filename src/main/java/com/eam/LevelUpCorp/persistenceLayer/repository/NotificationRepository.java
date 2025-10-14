package com.eam.LevelUpCorp.persistenceLayer.repository;


import com.eam.LevelUpCorp.persistenceLayer.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {



    List<NotificationEntity> findByUserIdOrderBySentDateDesc(Long userId);

    List<NotificationEntity> findByUserIdAndStatusOrderBySentDateDesc(Long userId, String status);

    int countByUserIdAndStatus(Long userId, String status);

    List<NotificationEntity> findByUserIdAndType(Long userId, String type);
}
