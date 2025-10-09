package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.AnswerResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.GradeAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.dto.SubmitAnswerDTO;

import java.util.List;

public interface AnswerService {

    AnswerResponseDTO submitAnswer(SubmitAnswerDTO submitAnswerDTO, Long userId, Long fileId);


    AnswerResponseDTO gradeAnswer(Long answerId, GradeAnswerDTO gradeAnswerDTO);


    AnswerResponseDTO getAnswerById(Long id);

    List<AnswerResponseDTO> getAllAnswers();

    List<AnswerResponseDTO> getAnswersByEvaluation(Long evaluationId);

    List<AnswerResponseDTO> getAnswersByUser(Long userId);

    AnswerResponseDTO getAnswerByEvaluationAndUser(Long evaluationId, Long userId);

    void deleteAnswer(Long id);


    boolean hasUserAnsweredEvaluation(Long evaluationId, Long userId);



}
