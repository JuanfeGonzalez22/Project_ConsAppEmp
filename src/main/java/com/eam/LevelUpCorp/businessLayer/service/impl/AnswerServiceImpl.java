package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.AnswerResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.GradeAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.dto.SubmitAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.service.AnswerService;
import com.eam.LevelUpCorp.businessLayer.validate.AnswerValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.AnswerDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {


    private final AnswerDAO answerDAO;
    private final AnswerValidate answerValidate;



    // private final AnswerValidate valiAnswer; // Lo comentamos por ahora

    /*

     */
    @Override
    public AnswerResponseDTO submitAnswer(SubmitAnswerDTO submitAnswerDTO, Long userId, Long fileId) {
        if (submitAnswerDTO == null){
            throw new IllegalArgumentException("Los datos de la respuesta son nulos");
        }

        log.info("Estudiante {} enviando respuesta para evaluación: {}", userId, submitAnswerDTO.getEvaluationId());

        answerValidate.validateSubmitAnswer(submitAnswerDTO, userId);

        AnswerResponseDTO respuestaCreada = answerDAO.save(submitAnswerDTO, userId, fileId);
        log.info("Respuesta enviada exitosamente con ID: {}", respuestaCreada.getId());

        return respuestaCreada;
    }

    /*

     */
    @Override
    public AnswerResponseDTO gradeAnswer(Long answerId, GradeAnswerDTO gradeAnswerDTO) {
        log.info("Calificando respuesta con ID: {}", answerId);

        answerValidate.validateGradeAnswer(gradeAnswerDTO);

        AnswerResponseDTO respuestaCalificada = answerDAO.update(answerId, gradeAnswerDTO)
                .orElseThrow(() -> {
                    log.warn("Error al calificar respuesta: {}", answerId);
                    return new RuntimeException("Respuesta no encontrada con ID: " + answerId);
                });

        log.info("Respuesta calificada exitosamente con puntaje: {}", respuestaCalificada.getScore());
        return respuestaCalificada;
    }

    /*

     */
    @Override
    @Transactional(readOnly = true)
    public AnswerResponseDTO getAnswerById(Long id) {
        log.info("Obteniendo respuesta por ID: {}", id);
        answerValidate.validateAnswerId(id);
        return answerDAO.findById(id).orElseThrow(() -> {
            log.warn("Error al obtener respuesta por ID: {}", id);
            return new RuntimeException("Respuesta no encontrada con ID: " + id);
        });
    }

    /*

     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponseDTO> getAllAnswers() {
        log.info("Obteniendo todas las respuestas");
        List<AnswerResponseDTO> respuestas = answerDAO.findAll();
        if (respuestas.isEmpty()) {
            log.warn("No se encontraron respuestas");
            throw new RuntimeException("No hay respuestas disponibles");
        }

        log.info("Se encontraron {} respuestas", respuestas.size());
        return respuestas;
    }

    /*

     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponseDTO> getAnswersByEvaluation(Long evaluationId) {
        log.info("Obteniendo respuestas para evaluación: {}", evaluationId);
        List<AnswerResponseDTO> respuestas = answerDAO.findByEvaluationId(evaluationId);
        if (respuestas.isEmpty()) {
            log.warn("No se encontraron respuestas para evaluación: {}", evaluationId);
            throw new RuntimeException("No se encontraron respuestas para evaluación: " + evaluationId);
        }

        log.info("Se encontraron {} respuestas para evaluación: {}", respuestas.size(), evaluationId);
        return respuestas;
    }

    /*

     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponseDTO> getAnswersByUser(Long userId) {
        log.info("Obteniendo respuestas para usuario: {}", userId);
        List<AnswerResponseDTO> respuestas = answerDAO.findByUserId(userId);
        if (respuestas.isEmpty()) {
            log.warn("No se encontraron respuestas para usuario: {}", userId);
            throw new RuntimeException("No se encontraron respuestas para usuario: " + userId);
        }

        log.info("Se encontraron {} respuestas para usuario: {}", respuestas.size(), userId);
        return respuestas;
    }

    /*

     */
    @Override
    @Transactional(readOnly = true)
    public AnswerResponseDTO getAnswerByEvaluationAndUser(Long evaluationId, Long userId) {
        log.info("Obteniendo respuesta para evaluación {} y usuario {}", evaluationId, userId);
        answerValidate.validateEvaluationAndUser(userId, evaluationId);
        return answerDAO.findByEvaluationIdAndUserId(evaluationId, userId)
                .orElseThrow(() -> {
                    log.warn("No se encontró respuesta para evaluación {} y usuario {}", evaluationId, userId);
                    return new RuntimeException(
                            String.format("No se encontró respuesta para evaluación %d y usuario %d",
                                    evaluationId, userId)
                    );
                });
    }

    /*

     */
    @Override
    public void deleteAnswer(Long id) {
        log.info("Eliminando respuesta con ID: {}", id);

        getAnswerById(id);
        answerValidate.validateDeleteAnswer(id);

        boolean eliminado = answerDAO.deleteById(id);
        if (!eliminado) {
            throw new RuntimeException("Error eliminando la respuesta");
        }
        log.info("Respuesta eliminada exitosamente ID: {}", id);
    }

    /*

     */
    @Override
    @Transactional(readOnly = true)
    public boolean hasUserAnsweredEvaluation(Long evaluationId, Long userId) {
        log.info("Verificando si usuario {} respondió evaluación {}", userId, evaluationId);
        boolean respondio = answerDAO.existsByEvaluationIdAndUserId(evaluationId, userId);
        log.info("Usuario {} respondió evaluación {}: {}", userId, evaluationId, respondio);
        return respondio;
    }
}
