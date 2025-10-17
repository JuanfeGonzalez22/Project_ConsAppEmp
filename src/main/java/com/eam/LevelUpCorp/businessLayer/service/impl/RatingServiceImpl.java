package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.*;
import com.eam.LevelUpCorp.businessLayer.service.RatingService;
import com.eam.LevelUpCorp.persistenceLayer.dao.AnswerDAO;
import com.eam.LevelUpCorp.persistenceLayer.dao.RatingDAO;
import com.eam.LevelUpCorp.persistenceLayer.dao.UserRatingDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {


    private final RatingDAO ratingDAO;
    private final UserRatingDAO userRatingDAO;
    private final AnswerDAO answerDAO;

    /*

     */
    @Override
    public RatingResponseDTO create(RatingDTO createDTO) {
        log.info("Creando nueva gamificación: {}", createDTO.getName());


        if (ratingDAO.existsByCode(createDTO.getCode())) {
            log.warn("El código {} ya existe", createDTO.getCode());
            throw new RuntimeException("El código ya existe: " + createDTO.getCode());
        }

        RatingResponseDTO createdRating = ratingDAO.save(createDTO);
        log.info("Gamificación creada exitosamente con ID: {}", createdRating.getId());

        return createdRating;
    }

    /*
    Método para buscar gamificación por ID
     */
    @Override
    @Transactional(readOnly = true)
    public RatingResponseDTO getRatingById(Long id) {
        log.info("Obteniendo gamificación por ID: {}", id);
        return ratingDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Gamificación no encontrada con ID: {}", id);
                    return new RuntimeException("Gamificación no encontrada con ID: " + id);
                });
    }

    /*
    Método para buscar gamificación por código
     */
    @Override
    @Transactional(readOnly = true)
    public RatingResponseDTO getRatingByCode(String code) {
        log.info("Obteniendo gamificación por código: {}", code);
        return ratingDAO.findByCode(code)
                .orElseThrow(() -> {
                    log.warn("Gamificación no encontrada con código: {}", code);
                    return new RuntimeException("Gamificación no encontrada con código: " + code);
                });
    }

    /*
    Método para obtener todas las gamificaciones
     */
    @Override
    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getAllRatings() {
        log.info("Obteniendo todas las gamificaciones");
        List<RatingResponseDTO> ratings = ratingDAO.findAll();
        if (ratings.isEmpty()) {
            log.warn("No se encontraron gamificaciones");
            throw new RuntimeException("No hay gamificaciones disponibles");
        }
        log.info("Se encontraron {} gamificaciones", ratings.size());
        return ratings;
    }

    /*
    Método para actualizar gamificación
     */
    @Override
    public RatingResponseDTO updateRating(Long id, RatingDTO createDTO) {
        log.info("Actualizando gamificación con ID: {}", id);
        getRatingById(id);

        // Si cambia el código, validar que no exista
        if (createDTO.getCode() != null) {
            Optional<RatingResponseDTO> existing = ratingDAO.findByCode(createDTO.getCode());
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                log.warn("El código {} ya existe en otra gamificación", createDTO.getCode());
                throw new RuntimeException("El código ya existe en otra gamificación: " + createDTO.getCode());
            }
        }

        RatingResponseDTO updatedRating = ratingDAO.update(id, createDTO)
                .orElseThrow(() -> new RuntimeException("Error actualizando la gamificación"));

        log.info("Gamificación actualizada exitosamente ID: {}", id);
        return updatedRating;
    }

    /*
    Método para eliminar gamificación
     */
    @Override
    public void deleteRating(Long id) {
        log.info("Eliminando gamificación con ID: {}", id);
        getRatingById(id); // Verificar que existe

        boolean deleted = ratingDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error eliminando la gamificación");
        }
        log.info("Gamificación eliminada exitosamente ID: {}", id);
    }

    /*
    Método para buscar gamificaciones por nombre
     */
    @Override
    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getRatingsByName(String name) {
        log.info("Buscando gamificaciones por nombre: {}", name);
        List<RatingResponseDTO> ratings = ratingDAO.findByName(name);
        if (ratings.isEmpty()) {
            log.warn("No se encontraron gamificaciones con nombre: {}", name);
            throw new RuntimeException("No se encontraron gamificaciones con nombre: " + name);
        }
        log.info("Se encontraron {} gamificaciones", ratings.size());
        return ratings;
    }

    /*
    Método para verificar si código existe
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        log.debug("Verificando si existe código: {}", code);
        return ratingDAO.existsByCode(code);
    }

    // ========== MÉTODOS DE GAMIFICACIÓN IMPLEMENTADOS ==========

    @Override
    public boolean asignarLogroUsuario(Long userId, String codeLogro) {
        log.info("Asignando logro {} al usuario {}", codeLogro, userId);

        // Buscar el logro por código
        RatingResponseDTO logro = getRatingByCode(codeLogro);

        // Verificar si ya tiene el logro
        if (userRatingDAO.existsByUserIdAndRatingId(userId, logro.getId())) {
            log.info("Usuario {} ya tiene el logro {}", userId, codeLogro);
            return false;
        }

        // Asignar logro al usuario
        UserRatingDTO userLogro = new UserRatingDTO();
        userLogro.setUserId(userId);
        userLogro.setRatingId(logro.getId());

        userRatingDAO.save(userLogro);
        log.info("Logro {} asignado exitosamente al usuario {}", codeLogro, userId);
        return true;
    }

    @Override
    public List<RatingResponseDTO> getLogrosUsuario(Long userId) {
        log.info("Obteniendo logros del usuario: {}", userId);

        List<UserRatingResponseDTO> userLogros = userRatingDAO.findByUserId(userId);
        return userLogros.stream()
                .map(userLogro -> getRatingById(userLogro.getRatingId()))
                .toList();
    }

    @Override
    public void verificarLogrosAutomatic(Long userId, String action, Object datosAction) {
        log.info("Verificando logros automáticos para usuario {} - Acción: {}", userId, action);

        switch (action) {
            case "RESPUESTA_ENVIADA":
                verificarLogrosRespuestas(userId, (Long) datosAction);
                break;
            case "EVALUACION_APROBADA":
                verificarLogrosEvaluaciones(userId, (Double) datosAction);
                break;
            default:
                log.warn("Acción no reconocida: {}", action);
        }
    }

    // ========== MÉTODOS PRIVADOS PARA LÓGICA DE GAMIFICACIÓN ==========

    private void verificarLogrosRespuestas(Long userId, Long evaluationId) {
        // Contar respuestas del usuario
        Long totalRespuestas = answerDAO.countByUserId(userId);

        // Logro: Primera respuesta
        if (totalRespuestas == 1) {
            asignarLogroUsuario(userId, "PRIMERA_RESPUESTA");
        }

        // Logro: Respondedor activo (10 respuestas)
        if (totalRespuestas >= 10) {
            asignarLogroUsuario(userId, "RESPONDEDOR_ACTIVO");
        }

        // Logro: Experto en respuestas (50 respuestas)
        if (totalRespuestas >= 50) {
            asignarLogroUsuario(userId, "EXPERTO_RESPUESTAS");
        }

        // Logro: Perfecto en evaluación (score = 100%)
        Optional<AnswerResponseDTO> respuesta = answerDAO.findByEvaluationIdAndUserId(evaluationId, userId);
        if (respuesta.isPresent() && Double.compare(respuesta.get().getScore(), 100.0) == 0) {
            asignarLogroUsuario(userId, "PERFECTO_EVALUACION");
        }

        if (totalRespuestas >= 5) {
            asignarLogroUsuario(userId, "RESPONDEDOR_NOVATO");
        }
        if (totalRespuestas >= 25) {
            asignarLogroUsuario(userId, "RESPONDEDOR_EXPERTO");
        }
        if (totalRespuestas >= 100) {
            asignarLogroUsuario(userId, "MAESTRO_RESPUESTAS");
        }
    }

    private void verificarLogrosEvaluaciones(Long userId, Double score) {
        if (score >= 90.0) {
            asignarLogroUsuario(userId, "EXCELENTE_CALIFICACION");
        }
        if (score >= 80.0) {
            asignarLogroUsuario(userId, "BUEN_ESTUDIANTE");
        }
        if (score >= 95.0) {
            asignarLogroUsuario(userId, "SOBRESALIENTE");
        }
         Long highScoreCount = answerDAO.countByUserIdAndScoreGreaterThanEqual(userId, 95.0);
         if (highScoreCount >= 5) {
             asignarLogroUsuario(userId, "SOBRESALIENTE");
         }
    }

}
