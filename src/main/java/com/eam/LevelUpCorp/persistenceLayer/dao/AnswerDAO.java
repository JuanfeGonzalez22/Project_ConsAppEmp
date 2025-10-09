package com.eam.LevelUpCorp.persistenceLayer.dao;

import com.eam.LevelUpCorp.businessLayer.dto.AnswerResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.GradeAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.dto.SubmitAnswerDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.AnswerEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.AnswerMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnswerDAO {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    //Save.
    public AnswerResponseDTO save(SubmitAnswerDTO submitAnswerDTO, Long userId, Long fileId) {

        AnswerEntity answerEntity = answerMapper.toEntity(submitAnswerDTO);
        answerEntity.setUserId(userId);
        answerEntity.setAnswerFileId(fileId);

        AnswerEntity savedEntity = answerRepository.save(answerEntity);
        return answerMapper.toDTO(savedEntity);
    }

    //Search by ID.
    public Optional<AnswerResponseDTO> findById(Long id) {
        return answerRepository.findById(id).map(answerMapper::toDTO);

    }

    // Search by Evaluation and User
    public Optional<AnswerResponseDTO> findByEvaluationIdAndUserId(Long evaluationId, Long userId) {
        return answerRepository.findByEvaluationIdAndUserId(evaluationId, userId)
                .map(answerMapper::toDTO);
    }

        //Update.
    public Optional<AnswerResponseDTO> update(Long id, GradeAnswerDTO gradeAnswerDTO) {

        return answerRepository.findById(id).map(existingEntity -> {
           answerMapper.updateEntityFromGradeDTO(gradeAnswerDTO, existingEntity);
           AnswerEntity updateEntity = answerRepository.save(existingEntity);
           return answerMapper.toDTO(updateEntity);
        });

    }

    //Delete.
    public boolean deleteById(Long id) {
        if (answerRepository.existsById(id)) {
            answerRepository.deleteById(id);
            return true;
        }
        return false;
    }



    // Find all answers by evaluation (para instructor)
    public List<AnswerResponseDTO> findByEvaluationId(Long evaluationId) {
        return answerRepository.findByEvaluationId(evaluationId)
                .stream()
                .map(answerMapper::toDTO)
                .toList();
    }

    // Find all answers by user.
    public List<AnswerResponseDTO> findByUserId(Long userId) {
        return answerRepository.findByUserId(userId)
                .stream()
                .map(answerMapper::toDTO)
                .toList();
    }



    // Check if user already answered an evaluation.
    public boolean existsByEvaluationIdAndUserId(Long evaluationId, Long userId) {
        return answerRepository.existsByEvaluationIdAndUserId(evaluationId, userId);
    }

    // Find ungraded answers (score = 0)
    public List<AnswerResponseDTO> findUngradedByEvaluationId(Long evaluationId) {
        return answerRepository.findByEvaluationIdAndScore(evaluationId, 0.0)
                .stream()
                .map(answerMapper::toDTO)
                .toList();
    }

    // Count answers by evaluation
    public Long countByEvaluationId(Long evaluationId) {
        return answerRepository.countByEvaluationId(evaluationId);
    }

    // Find all answers (para admin)
    public List<AnswerResponseDTO> findAll() {
        return answerRepository.findAll()
                .stream()
                .map(answerMapper::toDTO)
                .toList();
    }


    // Count answers by user - Contar respuestas de un usuario
    public Long countByUserId(Long userId) {
        return answerRepository.countByUserId(userId);
    }

    // Count high scores by user - Contar respuestas con alta calificación
    public Long countByUserIdAndScoreGreaterThanEqual(Long userId, Double minScore) {
        return answerRepository.countByUserIdAndScoreGreaterThanEqual(userId, minScore);
    }


}
