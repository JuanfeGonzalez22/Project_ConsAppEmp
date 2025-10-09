package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.UserRatingDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserRatingResponseDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserRatingEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.UserRatingMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.UserRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRatingDAO {



    private final UserRatingRepository userRatingRepository;
    private final UserRatingMapper userRatingMapper;

    // Save
    public UserRatingResponseDTO save(UserRatingDTO createDTO) {
        UserRatingEntity entity = userRatingMapper.toEntity(createDTO);
        UserRatingEntity savedEntity = userRatingRepository.save(entity);
        return userRatingMapper.toResponseDTO(savedEntity);
    }

    // Search by ID
    public Optional<UserRatingResponseDTO> findById(Long id) {
        return userRatingRepository.findById(id)
                .map(userRatingMapper::toResponseDTO);
    }

    // Check if user has achievement
    public boolean existsByUserIdAndRatingId(Long userId, Long ratingId) {
        return userRatingRepository.existsByUserIdAndRatingId(userId, ratingId);
    }

    // Get user achievements
    public List<UserRatingResponseDTO> findByUserId(Long userId) {
        return userRatingRepository.findByUserId(userId)
                .stream()
                .map(userRatingMapper::toResponseDTO)
                .toList();
    }

    // Get users with achievement
    public List<UserRatingResponseDTO> findByRatingId(Long ratingId) {
        return userRatingRepository.findByRatingId(ratingId)
                .stream()
                .map(userRatingMapper::toResponseDTO)
                .toList();
    }

    // Count user achievements
    public Long countByUserId(Long userId) {
        return userRatingRepository.countByUserId(userId);
    }

    // Count achievement users
    public Long countByRatingId(Long ratingId) {
        return userRatingRepository.countByRatingId(ratingId);
    }

    // Delete user achievement
    public boolean deleteByUserIdAndRatingId(Long userId, Long ratingId) {
        if (userRatingRepository.existsByUserIdAndRatingId(userId, ratingId)) {
            userRatingRepository.deleteByUserIdAndRatingId(userId, ratingId);
            return true;
        }
        return false;
    }

    // Find specific user achievement
    public Optional<UserRatingResponseDTO> findByUserIdAndRatingId(Long userId, Long ratingId) {
        return userRatingRepository.findByUserIdAndRatingId(userId, ratingId)
                .map(userRatingMapper::toResponseDTO);
    }
}
