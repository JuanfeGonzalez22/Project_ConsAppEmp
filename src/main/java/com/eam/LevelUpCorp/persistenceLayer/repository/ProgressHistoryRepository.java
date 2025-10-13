package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.ProgressHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface ProgressHistoryRepository extends JpaRepository<ProgressHistoryEntity, Long> {


    //Metodo para contar modulos completados de un curso.
    @Query("SELECT COUNT(DISTINCT ph.moduleId) FROM ProgressHistoryEntity ph WHERE ph.registrationId = " +
            ":registrationId AND ph.courseId = :courseId AND ph.status = 'COMPLETED'")
    int countCompletedModules(@Param("registrationId") Long registrationId, @Param("courseId") Long courseId);

    //Metodo para calcular el tiempo total dedicado en el curso.
    @Query("SELECT SUM(ph.timeDedicated) FROM ProgressHistoryEntity ph WHERE ph.registrationId " +
            "= :registrationId AND ph.courseId = :courseId")
    LocalTime calculateTotalTimeDedicated(@Param("registrationId") Long registrationId, @Param("courseId") Long courseId);

    //Metodo para verificar si un modulo esta completado.
    boolean existsByRegistrationIdAndModuleIdAndStatus(Long registrationId, Long moduleId, String status);


    //Metodo para obtener historial de usuario por curso.
    List<ProgressHistoryEntity> findByRegistrationIdAndCourseId(Long registrationId, Long courseId);
}
