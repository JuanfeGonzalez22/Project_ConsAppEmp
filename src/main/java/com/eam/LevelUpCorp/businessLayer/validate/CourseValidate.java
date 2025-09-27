package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;

public class CourseValidate {


    public void validateCreate(CourseDTO courseDTO) {
        if (courseDTO == null) {
            throw new IllegalArgumentException("El curso es nulo");
        }
        if (courseDTO.getTitulo() == null || courseDTO.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del curso es obligatorio");
        }
        if (courseDTO.getDescripcion() == null || courseDTO.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del curso es obligatoria");
        }
        if (courseDTO.getDuracionEstimada() == null) {
            throw new IllegalArgumentException("La duración estimada es obligatoria");
        }
        if (courseDTO.getNivel() < 1 || courseDTO.getNivel() > 3) {
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
