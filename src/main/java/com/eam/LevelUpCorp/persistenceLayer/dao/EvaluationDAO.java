package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.EvaluationEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.EvaluationMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EvaluationDAO {

    private final EvaluationRepository evaluationRepository;
    private final EvaluationMapper evaluationMapper;

    //Save
    public EvaluationDTO save(EvaluationDTO evaluationDTO) {
        EvaluationEntity evaluationEntity = evaluationMapper.toEntity(evaluationDTO);
        EvaluationEntity savedEvaluationEntity = evaluationRepository.save(evaluationEntity);
        return evaluationMapper.toDTO(savedEvaluationEntity);

    }


    //Search
    public Optional<EvaluationDTO> findById(Long id) {
        return evaluationRepository.findById(id).map(evaluationMapper::toDTO);

    }


    //Update
    public Optional<EvaluationDTO> update(Long id, EvaluationDTO evaluationDTO) {
        return evaluationRepository.findById(id)
                .map(existingEntity -> {
                    evaluationMapper.updateEntity(evaluationDTO, existingEntity);
                    EvaluationEntity updatedEntity = evaluationRepository.save(existingEntity);
                    return evaluationMapper.toDTO(updatedEntity);
                });
    }



    //Delete
    public boolean deleteById(Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //All evaluations.
    public List<EvaluationDTO> findAll() {
        return evaluationRepository.findAll()
                .stream().map(evaluationMapper::toDTO).toList();
    }

    //Get all evaluations by Module.
    public List<EvaluationDTO> findByModuleId(Long moduleId) {
        return evaluationRepository.findByModuleId(moduleId)
                .stream()
                .map(evaluationMapper::toDTO)
                .toList();
    }

}
