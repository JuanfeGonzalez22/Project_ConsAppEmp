package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.ProgressHistoryDTO;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ProgressHistoryValidate {


    public void validateMarkModuleAsCompleted(Long registrationId, Long moduleId, LocalTime timeDedicated) {
        validateRegistrationId(registrationId);
        validateModuleId(moduleId);
        validateTimeDedicated(timeDedicated);
    }

    public void validateGetCurrentProgress(Long registrationId) {
        validateRegistrationId(registrationId);
    }

    public void validateGetProgressHistory(Long registrationId) {
        validateRegistrationId(registrationId);
    }

    public void validateIsModuleCompleted(Long registrationId, Long moduleId) {
        validateRegistrationId(registrationId);
        validateModuleId(moduleId);
    }

    public void validateProgressHistoryDTO(ProgressHistoryDTO progressHistoryDTO) {
        if (progressHistoryDTO == null) {
            throw new IllegalArgumentException("El historial de progreso no puede ser nulo");
        }
        validateUserId(progressHistoryDTO.getUserId());
        validateCourseId(progressHistoryDTO.getCourseId());
        validateModuleId(progressHistoryDTO.getModuleId());
        validateRegistrationId(progressHistoryDTO.getRegistrationId());
        validateTimeDedicated(progressHistoryDTO.getTimeDedicated());
        validateProgress(progressHistoryDTO.getModuleProgress());
    }

    private void validateRegistrationId(Long registrationId) {
        if (registrationId == null || registrationId <= 0) {
            throw new IllegalArgumentException("Registration ID es obligatorio y debe ser mayor a cero");
        }
    }

    private void validateModuleId(Long moduleId) {
        if (moduleId == null || moduleId <= 0) {
            throw new IllegalArgumentException("Module ID es obligatorio y debe ser mayor a cero");
        }
    }

    private void validateCourseId(Long courseId) {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID es obligatorio y debe ser mayor a cero");
        }
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID es obligatorio y debe ser mayor a cero");
        }
    }

    private void validateTimeDedicated(LocalTime timeDedicated) {
        if (timeDedicated == null) {
            throw new IllegalArgumentException("El tiempo dedicado no puede ser nulo");
        }
        if (timeDedicated.isBefore(LocalTime.of(0, 1))) {
            throw new IllegalArgumentException("El tiempo dedicado debe ser al menos 1 minuto");
        }
        if (timeDedicated.isAfter(LocalTime.of(23, 59))) {
            throw new IllegalArgumentException("El tiempo dedicado no puede exceder 23 horas y 59 minutos");
        }
    }

    private void validateProgress(double progress) {
        if (progress < 0.0 || progress > 100.0) {
            throw new IllegalArgumentException("El progreso debe estar entre 0% y 100%");
        }
    }

    public void validateProgressPercentage(double progress) {
        if (progress < 0.0 || progress > 100.0) {
            throw new IllegalArgumentException("El porcentaje de progreso debe estar entre 0 y 100");
        }
    }

    public void validateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede ser nulo o vacío");
        }
        if (!status.equals("IN_PROGRESS") && !status.equals("COMPLETED") && !status.equals("PENDING")) {
            throw new IllegalArgumentException("Estado inválido. Debe ser: IN_PROGRESS, COMPLETED o PENDING");
        }
    }

}
