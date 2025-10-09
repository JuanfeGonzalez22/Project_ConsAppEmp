package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EvaluationValidate {


    public void validateCreate(EvaluationDTO evaluationDTO) {
        if (evaluationDTO == null) {
            throw new IllegalArgumentException("Evaluation cannot be null");
        }
        if (!StringUtils.hasText(evaluationDTO.getTitle())) {
            throw new IllegalArgumentException("Evaluation title cannot be empty");
        }
        if (!StringUtils.hasText(evaluationDTO.getType())) {
            throw new IllegalArgumentException("Evaluation type cannot be empty");
        }
        if (evaluationDTO.getModuleId() == null) {
            throw new IllegalArgumentException("Module ID cannot be null");
        }
        if (evaluationDTO.getMaxScore() < 0) {
            throw new IllegalArgumentException("Max score cannot be negative");
        }

    }


    private void validateIdE(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid evaluation ID: " + id);
        }
    }

    public void validateById(Long id) {
        validateIdE(id);
    }


    public void validateUpdate(Long id, EvaluationDTO evaluationDTO) {
        validateIdE(id);

        validateCreate(evaluationDTO);
    }

    public void validateDelete(Long id) {
        validateIdE(id);
    }


}
