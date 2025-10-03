package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
//    @Query("SELECT c FROM CourseEntity c WHERE c.instructor.id = :instructorId")
//    List<CourseEntity> findByInstructorId(Long instructorId);
}
