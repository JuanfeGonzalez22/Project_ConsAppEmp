package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {

    List<EvaluationEntity> findByModuleId(Long moduleId);

    @Query("""
    SELECT AVG(e.maxScore)
    FROM EvaluationEntity e
    JOIN ModuleEntity m ON e.moduleId = m.id
    WHERE m.courseId = :courseId
""")
    Double findAverageScoreByCourse(@Param("courseId") Long courseId);


}
