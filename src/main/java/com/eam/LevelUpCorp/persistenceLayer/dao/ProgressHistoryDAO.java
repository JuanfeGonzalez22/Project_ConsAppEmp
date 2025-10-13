package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.ProgressHistoryDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ProgressHistoryEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ProgressHistoryMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.ProgressHistoryRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProgressHistoryDAO {


    private final ProgressHistoryRepository progressHistoryRepository;
    private final ProgressHistoryMapper progressHistoryMapper;


    //Save
    public ProgressHistoryDTO save(ProgressHistoryDTO progressHistoryDTO) {
        ProgressHistoryEntity progressHistoryEntity = progressHistoryMapper.toEntity(progressHistoryDTO);
        ProgressHistoryEntity savedEntity = progressHistoryRepository.save(progressHistoryEntity);
        return progressHistoryMapper.toDTO(savedEntity);

    }

    //Find by ID
    public Optional<ProgressHistoryDTO> findById(Long id) {
        return progressHistoryRepository.findById(id)
                .map(progressHistoryMapper::toDTO);

    }


    //Get progress history by registration and course
    public List<ProgressHistoryDTO> findByRegistrationNadCourse(Long registrationId, Long courseId) {
        List<ProgressHistoryEntity> entities = progressHistoryRepository.findByRegistrationIdAndCourseId(registrationId, courseId);
        return progressHistoryMapper.toDTOList(entities);

    }

    //Calculate total time dedicated
    public LocalTime calculateTotalTimeDedicated(Long registrationId, Long courseId) {
        return progressHistoryRepository.calculateTotalTimeDedicated(registrationId, courseId);

    }

    // Count completed modules for a registration and course
    public int countCompletedModules(Long registrationId, Long courseId) {
        return progressHistoryRepository.countCompletedModules(registrationId, courseId);
    }


    // Check if module is completed
    public boolean isModuleCompleted(Long registrationId, Long moduleId) {
        return progressHistoryRepository.existsByRegistrationIdAndModuleIdAndStatus(registrationId, moduleId, "COMPLETED");
    }

    // Delete progress history
    public boolean deleteById(Long id) {
        if (progressHistoryRepository.existsById(id)) {
            progressHistoryRepository.deleteById(id);
            return true;
        }
        return false;
    }




}
