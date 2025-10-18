package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;
import com.eam.LevelUpCorp.businessLayer.service.EvaluationService;
import com.eam.LevelUpCorp.businessLayer.validate.EvaluationValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.EvaluationDAO;
import com.eam.LevelUpCorp.persistenceLayer.mapper.EvaluationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EvaluationServiceImpl implements EvaluationService {


    private final EvaluationDAO evaluationDAO;
    private final EvaluationValidate evaluationValidate;


    /*
        Method for create an evaluation.
     */
    @Override
    public EvaluationDTO create(EvaluationDTO evaluationDTO) {
        if(evaluationDTO == null){
            throw new IllegalArgumentException("Evaluation cannot be null");
        }
        log.info("Creating a new evaluation: {}", evaluationDTO.getTitle());
        evaluationValidate.validateCreate(evaluationDTO);
        EvaluationDTO createdEvaluation = evaluationDAO.save(evaluationDTO);
        log.info("Evaluation created successfully with ID: {}", createdEvaluation);
        return createdEvaluation;
    }


    /*
        Method for get an evaluation by ID.
     */
    @Override
    @Transactional(readOnly = true)
    public EvaluationDTO getById(Long id) {
        log.info("Getting evaluation by ID: {}", id);
        evaluationValidate.validateById(id);
        return evaluationDAO.findById(id)
                .orElseThrow(() -> {log.warn("Evaluation with ID {} not found", id);
                    return new RuntimeException("Evaluation not found with ID " + id);
                });
    }


    /*
        Method for get all evaluations.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvaluationDTO> findAll() {
        log.info("Getting all Evaluations");
        List<EvaluationDTO> evaluations = evaluationDAO.findAll();
        if (evaluations.isEmpty()) {
            log.warn("No evaluations found");
            throw new RuntimeException("No evaluations available");
        }
        log.info("Found {} evaluations", evaluations.size());
        return evaluations;
    }


    /*
        Method for delete a evaluation.
     */
    @Override
    public void deleteById(Long id) {
        log.info("Deleting Evaluation by ID: {}", id);
        evaluationValidate.validateDelete(id);
        boolean deleted = evaluationDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error deleting Evaluation with ID: " + id);
        }
        log.info("Evaluation deleted successfully with ID: {}", id);

    }


    /*
        Method for update an evaluation.
     */
    @Override
    public EvaluationDTO update(Long id, EvaluationDTO evaluationDTO) {
        log.info("Updating Evaluation by ID: {}", id);
        evaluationValidate.validateUpdate(id,evaluationDTO);
        EvaluationDTO updated = evaluationDAO.update(id, evaluationDTO)
                .orElseThrow(() -> new RuntimeException("Error updating Evaluation"));
        log.info("Evaluation updated successfully with ID: {}", id);
        return updated;
    }


    /*
        Method for get all evaluations by module.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvaluationDTO> getEvaluationByModuleId(Long moduleId) {
        log.info("Getting Evaluations by Module ID: {}", moduleId);

        List<EvaluationDTO> evaluations = evaluationDAO.findByModuleId(moduleId);

        if (evaluations.isEmpty()) {
            log.warn("No evaluations found for Module ID: {}", moduleId);
            throw new RuntimeException("No evaluations available for module ID: " + moduleId);
        }

        log.info("Found {} evaluations for Module ID: {}", evaluations.size(), moduleId);
        return evaluations;
    }



}
