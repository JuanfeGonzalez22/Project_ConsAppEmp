package com.eam.LevelUpCorp.businessLayer.validate;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import org.springframework.stereotype.Component;


@Component
public class RegistrationValidate {

    public void validateCreate(RegistrationDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El registro esta nulo");

        if (dto.getFullName() == null || dto.getFullName().isBlank()) {
            throw new IllegalArgumentException("Es requerido el nombre completo");
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es requerido");
        }

        if (dto.getPassword() == null || dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe ser de 8 caracteres de largo");
        }

        if (dto.getRole() == null || dto.getRole().isBlank()) {
            throw new IllegalArgumentException("El rol es requerido");
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
