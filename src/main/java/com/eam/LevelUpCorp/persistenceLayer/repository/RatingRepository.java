package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {


    Optional<RatingEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<RatingEntity> findByNameContainingIgnoreCase(String name);

    List<RatingEntity> findByCriterionContainingIgnoreCase(String criterion);


}
