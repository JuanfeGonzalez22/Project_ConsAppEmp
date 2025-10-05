package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.CourseInstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseInstructorRepository extends JpaRepository<CourseInstructorEntity, Long> {



    List<CourseInstructorEntity> findByInstructorId(Long instructorId);


}
