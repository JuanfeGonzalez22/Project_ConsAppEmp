package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.FileResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileResourceRepository extends JpaRepository<FileResourceEntity, Long> {



    List<FileResourceEntity> findByModuleId(Long moduleId);

    // Archivos de evaluación (subidos por instructor)
    List<FileResourceEntity> findByEvaluationId(Long evaluationId);

    // Archivos de respuesta (subidos por estudiantes)
    List<FileResourceEntity> findByAnswerId(Long answerId);

    // Buscar archivo específico por evaluación
    Optional<FileResourceEntity> findByEvaluationIdAndFileName(Long evaluationId, String fileName);

    // Verificar si existe archivo para una evaluación
    boolean existsByEvaluationId(Long evaluationId);
}
