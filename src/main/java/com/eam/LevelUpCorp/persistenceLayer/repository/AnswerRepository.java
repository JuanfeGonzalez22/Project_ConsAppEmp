package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {



    List<AnswerEntity> findByEvaluationId(Long evaluationId);

    List<AnswerEntity> findByUserId(Long userId);

    Optional<AnswerEntity> findByEvaluationIdAndUserId(Long evaluationId, Long userId);

    long countByEvaluationId(Long evaluationId);

    boolean existsByEvaluationIdAndUserId(Long evaluationId, Long userId);

    List<AnswerEntity> findByEvaluationIdAndScore(Long evaluationId, double score);


}
