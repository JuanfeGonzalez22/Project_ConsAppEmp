package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class CourseValidate {

    public void validateCreate(CourseDTO courseDTO) {
        if (courseDTO == null) {
            throw new IllegalArgumentException("El curso es nulo");
        }
        if (courseDTO.getTitle() == null || courseDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del curso es obligatorio");
        }
        if (courseDTO.getDescription() == null || courseDTO.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del curso es obligatoria");
        }
        if (courseDTO.getEstimatedDuration() == null) {
            throw new IllegalArgumentException("La duración estimada es obligatoria y debe ser mayor a 0");
        }
        if (courseDTO.getLevel() < 1 || courseDTO.getLevel() > 3) {
            throw new IllegalArgumentException("El nivel del curso debe estar entre 1 (básico) y 3 (avanzado)");
        }
    }

    public void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para buscar curso");
        }
    }

    public void validateUpdate(Long id, CourseDTO courseDTO) {
        validateSearch(id);
        validateCreate(courseDTO);
    }

    public void validateDelete(Long id) {
        validateSearch(id);
    }
}