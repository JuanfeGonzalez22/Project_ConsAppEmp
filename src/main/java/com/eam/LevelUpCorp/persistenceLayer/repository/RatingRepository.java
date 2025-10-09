package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {


    Optional<RatingEntity> findByCode(String codigo);

    boolean existsByCode(String codigo);

    List<RatingEntity> findByNameContainingIgnoreCase(String nombre);

    List<RatingEntity> findByCriterioContainingIgnoreCase(String criterio);


}
