package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.RatingDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RatingResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.RatingService;
import com.eam.LevelUpCorp.persistenceLayer.dao.RatingDAO;
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
    // private final UserGamificacionDAO userGamificacionDAO; // Para cuando tengas la relación con usuarios
    // private final AnswerDAO answerDAO; // Para la lógica de logros automáticos

    /*

     */
    @Override
    public RatingResponseDTO create(RatingDTO createDTO) {
        log.info("Creando nueva gamificación: {}", createDTO.getName());

        // Validar que el código no exista
//        if (gamificacionDAO.existsByCodigo(createDTO.getCodigo())) {
//            log.warn("El código {} ya existe", createDTO.getCodigo());
//            throw new RuntimeException("El código ya existe: " + createDTO.getCodigo());
//        }

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

    // ========== MÉTODOS DE GAMIFICACIÓN ==========

    @Override
    public boolean asignarLogroUsuario(Long userId, String codeLogro) {
        log.info("Asignando logro {} al usuario {}", codeLogro, userId);
        // TODO: Implementar cuando tengas UserGamificacionDAO
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    @Override
    public List<RatingResponseDTO> getLogrosUsuario(Long userId) {
        log.info("Obteniendo logros del usuario: {}", userId);
        // TODO: Implementar cuando tengas UserGamificacionDAO
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    @Override
    public void verificarLogrosAutomatic(Long userId, String action, Object datosAction) {
        log.info("Verificando logros automáticos para usuario {} - Acción: {}", userId, action);
        // TODO: Implementar lógica de logros automáticos
        throw new UnsupportedOperationException("Método no implementado aún");
    }
}
