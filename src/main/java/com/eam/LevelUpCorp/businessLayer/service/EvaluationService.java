package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;

import java.util.List;

public interface EvaluationService {


    //Create.
    EvaluationDTO create(EvaluationDTO evaluationDTO);

    //Search.
    EvaluationDTO getById(Long id);

    //Get all.
    List<EvaluationDTO> findAll();

    //Delete.
    void deleteById(Long id);

    //Update.
    EvaluationDTO update(Long id, EvaluationDTO evaluationDTO);

    //Get all evaluations to module.
    List<EvaluationDTO> getEvaluationByModuleId(Long moduleId);
}
