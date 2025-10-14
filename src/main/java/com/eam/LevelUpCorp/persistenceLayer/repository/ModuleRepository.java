package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.ModuleEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {


    int countByCourseId(Long courseId);


}
