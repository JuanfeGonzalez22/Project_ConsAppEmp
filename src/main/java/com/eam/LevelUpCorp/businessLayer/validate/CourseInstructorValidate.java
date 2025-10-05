package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorDTO;
import org.springframework.stereotype.Component;

@Component
public class CourseInstructorValidate {

    // Validación al crear una asignación de curso-instructor
    public void validateCreate(CourseInstructorDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("The CourseInstructor is null");
        }
        if (dto.getCourseId() == null || dto.getCourseId() <= 0) {
            throw new IllegalArgumentException("The Course ID is invalid");
        }
        if (dto.getInstructorId() == null || dto.getInstructorId() <= 0) {
            throw new IllegalArgumentException("The Instructor ID is invalid");
        }
    }

    // Validación para ID genérico (buscar, actualizar, eliminar)
    public void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }

    // Validación al actualizar (similar a crear, pero admite que algunos campos sean opcionales)
    public void validateUpdate(Long id, CourseInstructorDTO dto) {
        validateSearch(id);
        if (dto == null) {
            throw new IllegalArgumentException("The CourseInstructor data is null");
        }
        if (dto.getCourseId() != null && dto.getCourseId() <= 0) {
            throw new IllegalArgumentException("The Course ID is invalid");
        }
        if (dto.getInstructorId() != null && dto.getInstructorId() <= 0) {
            throw new IllegalArgumentException("The Instructor ID is invalid");
        }
    }

    // Validación para eliminar
    public void validateDelete(Long id) {
        validateSearch(id);
    }
}
