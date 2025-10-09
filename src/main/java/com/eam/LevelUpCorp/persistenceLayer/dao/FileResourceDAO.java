package com.eam.LevelUpCorp.persistenceLayer.dao;

import com.eam.LevelUpCorp.businessLayer.dto.FileResourceDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.FileResourceEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.FileResourceMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.FileResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class FileResourceDAO {

    private final FileResourceRepository fileResourceRepository;
    private final FileResourceMapper fileResourceMapper;

    // Save
    public FileResourceDTO save(FileResourceDTO fileResourceDTO) {
        FileResourceEntity fileResourceEntity = fileResourceMapper.toEntity(fileResourceDTO);
        FileResourceEntity savedEntity = fileResourceRepository.save(fileResourceEntity);
        return fileResourceMapper.toDTO(savedEntity);
    }

    // Search by ID
    public Optional<FileResourceDTO> findById(Long id) {
        return fileResourceRepository.findById(id)
                .map(fileResourceMapper::toDTO);
    }

    // Update
    public Optional<FileResourceDTO> update(Long id, FileResourceDTO fileResourceDTO) {
        return fileResourceRepository.findById(id)
                .map(existingEntity -> {
                    // Actualizar campos manualmente (sin updateEntityFromDTO)
                    existingEntity.setFileName(fileResourceDTO.getFileName());
                    existingEntity.setFileType(fileResourceDTO.getFileType());
                    existingEntity.setModuleId(fileResourceDTO.getModuleId());
                    existingEntity.setEvaluationId(fileResourceDTO.getEvaluationId());
                    existingEntity.setAnswerId(fileResourceDTO.getAnswerId());

                    FileResourceEntity updatedEntity = fileResourceRepository.save(existingEntity);
                    return fileResourceMapper.toDTO(updatedEntity);
                });
    }

    // Delete
    public boolean deleteById(Long id) {
        if (fileResourceRepository.existsById(id)) {
            fileResourceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // All Files
    public List<FileResourceDTO> findAll() {
        return fileResourceRepository.findAll()
                .stream()
                .map(fileResourceMapper::toDTO)
                .toList();
    }

    // Find by Module ID
    public List<FileResourceDTO> findByModuleId(Long moduleId) {
        return fileResourceRepository.findByModuleId(moduleId)
                .stream()
                .map(fileResourceMapper::toDTO)
                .toList();
    }

    // Find by Evaluation ID
    public List<FileResourceDTO> findByEvaluationId(Long evaluationId) {
        return fileResourceRepository.findByEvaluationId(evaluationId)
                .stream()
                .map(fileResourceMapper::toDTO)
                .toList();
    }

    // Find by Answer ID
    public List<FileResourceDTO> findByAnswerId(Long answerId) {
        return fileResourceRepository.findByAnswerId(answerId)
                .stream()
                .map(fileResourceMapper::toDTO)
                .toList();
    }

    // Check if evaluation has files
    public boolean existsByEvaluationId(Long evaluationId) {
        return fileResourceRepository.existsByEvaluationId(evaluationId);
    }


}
