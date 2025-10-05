package com.eam.LevelUpCorp.businessLayer.validate;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import org.springframework.stereotype.Component;


@Component
public class RegistrationValidate {

    public void validateCreate(RegistrationDTO dto) {
        if (dto == null)
            throw new IllegalArgumentException("El registro está nulo");

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }

        if (dto.getCourseId() == null) {
            throw new IllegalArgumentException("El ID del curso es requerido");
        }

        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new IllegalArgumentException("El estado es requerido");
        }
    }

    public void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido para buscar un registro ");
        }
    }

    public void validateUpdate(Long id, RegistrationDTO dto) {
        validateSearch(id);
        validateCreate(dto);
    }

    public void validateDelete(Long id) {
        validateSearch(id);
    }
}
