package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.UserRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRatingEntity, Long> {


    // Verificar si usuario ya tiene un logro específico
    boolean existsByUserIdAndRatingId(Long userId, Long ratingId);

    // Obtener todos los logros de un usuario
    List<UserRatingEntity> findByUserId(Long userId);

    // Obtener todos los usuarios que tienen un logro específico
    List<UserRatingEntity> findByRatingId(Long ratingId);

    // Contar cuántos usuarios tienen un logro específico
    Long countByRatingId(Long ratingId);

    // Contar cuántos logros tiene un usuario
    Long countByUserId(Long userId);

    // Buscar relación específica usuario-logro
    Optional<UserRatingEntity> findByUserIdAndRatingId(Long userId, Long ratingId);

    // Eliminar relación usuario-logro específica
    void deleteByUserIdAndRatingId(Long userId, Long ratingId);

}
