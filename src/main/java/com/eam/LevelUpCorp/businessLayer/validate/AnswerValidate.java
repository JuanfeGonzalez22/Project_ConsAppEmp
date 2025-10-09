package com.eam.LevelUpCorp.businessLayer.validate;


import com.eam.LevelUpCorp.businessLayer.dto.GradeAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.dto.SubmitAnswerDTO;
import org.springframework.stereotype.Component;
@Component
public class AnswerValidate {

    public void validateSubmitAnswer(SubmitAnswerDTO submitAnswerDTO, Long userId) {
        if (submitAnswerDTO == null) {
            throw new IllegalArgumentException("Los datos de la respuesta son nulos");
        }
        if (submitAnswerDTO.getEvaluationId() == null) {
            throw new IllegalArgumentException("El ID de evaluación es requerido");
        }
        if (submitAnswerDTO.getEvaluationId() <= 0) {
            throw new IllegalArgumentException("El ID de evaluación debe ser mayor a 0");
        }
        validateUserId(userId);
    }

    public void validateGradeAnswer(GradeAnswerDTO gradeAnswerDTO) {
        if (gradeAnswerDTO == null) {
            throw new IllegalArgumentException("Los datos de calificación son nulos");
        }
        if (gradeAnswerDTO.getScore() == null) {
            throw new IllegalArgumentException("La calificación es requerida");
        }
        if (gradeAnswerDTO.getScore() < 0 || gradeAnswerDTO.getScore() > 100) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 100");
        }
        if (gradeAnswerDTO.getFeedBack() != null && gradeAnswerDTO.getFeedBack().length() > 500) {
            throw new IllegalArgumentException("El feedback no puede exceder 500 caracteres");
        }
    }

    public void validateAnswerId(Long answerId) {
        if (answerId == null) {
            throw new IllegalArgumentException("El ID de respuesta es requerido");
        }
        if (answerId <= 0) {
            throw new IllegalArgumentException("El ID de respuesta debe ser mayor a 0");
        }
    }

    public void validateEvaluationId(Long evaluationId) {
        if (evaluationId == null) {
            throw new IllegalArgumentException("El ID de evaluación es requerido");
        }
        if (evaluationId <= 0) {
            throw new IllegalArgumentException("El ID de evaluación debe ser mayor a 0");
        }
    }

    public void validateUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("El ID de usuario es requerido");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("El ID de usuario debe ser mayor a 0");
        }
    }

//    public void validateSearchAnswer(Long answerId) {
//        validateAnswerId(answerId);
//    }
//
//    public void validateUpdateAnswer(Long answerId, GradeAnswerDTO gradeAnswerDTO) {
//        validateAnswerId(answerId);
//        validateGradeAnswer(gradeAnswerDTO);
//    }

    public void validateDeleteAnswer(Long answerId) {
        validateAnswerId(answerId);
    }

    public void validateEvaluationAndUser(Long evaluationId, Long userId) {
        validateEvaluationId(evaluationId);
        validateUserId(userId);
    }
}