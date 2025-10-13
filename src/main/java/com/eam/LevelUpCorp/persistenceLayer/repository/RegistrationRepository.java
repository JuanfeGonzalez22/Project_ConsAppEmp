package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

    long countByCourseId(Long courseId);

    Optional<RegistrationEntity> findByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT AVG(r.progress) FROM RegistrationEntity r WHERE r.courseId = :courseId")
    Double findAverageProgressByCourseId(@Param("courseId") Long courseId);
}
