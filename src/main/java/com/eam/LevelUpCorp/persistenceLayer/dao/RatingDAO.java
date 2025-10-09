package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.RatingDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RatingResponseDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.RatingEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.RatingMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RatingDAO {


    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    // Save
    public RatingResponseDTO save(RatingDTO createDTO) {
        RatingEntity ratingEntity = ratingMapper.toEntity(createDTO);
        RatingEntity savedEntity = ratingRepository.save(ratingEntity);
        return ratingMapper.toDTO(savedEntity);
    }

    // Search by ID
    public Optional<RatingResponseDTO> findById(Long id) {
        return ratingRepository.findById(id)
                .map(ratingMapper::toDTO);
    }

    // Search by códe
    public Optional<RatingResponseDTO> findByCode(String code) {
        return ratingRepository.findByCode(code)
                .map(ratingMapper::toDTO);
    }

    // Update
    public Optional<RatingResponseDTO> update(Long id, RatingDTO createDTO) {
        return ratingRepository.findById(id)
                .map(existingEntity -> {
                    ratingMapper.updateEntityFromDTO(createDTO, existingEntity);
                    RatingEntity updatedEntity = ratingRepository.save(existingEntity);
                    return ratingMapper.toDTO(updatedEntity);
                });
    }

    // Delete
    public boolean deleteById(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // All Ratings
    public List<RatingResponseDTO> findAll() {
        return ratingRepository.findAll()
                .stream()
                .map(ratingMapper::toDTO)
                .toList();
    }

    // Check if códe exists
    public boolean existsByCode(String code) {
        return ratingRepository.existsByCode(code);
    }

    // Search by name
    public List<RatingResponseDTO> findByName(String name) {
        return ratingRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ratingMapper::toDTO)
                .toList();
    }


}
