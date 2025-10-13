package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.ProgressHistoryDTO;

import java.time.LocalTime;
import java.util.List;

public interface ProgressHistoryService {


    // Confirmar módulo como completado
    ProgressHistoryDTO markModuleAsCompleted(Long registrationId, Long moduleId, LocalTime timeDedicated);

    // Obtener progreso actual del usuario en un curso
    ProgressHistoryDTO getCurrentProgress(Long registrationId);

    // Obtener historial completo de progreso
    List<ProgressHistoryDTO> getProgressHistory(Long registrationId);

    // Verificar si un módulo está completado
    boolean isModuleCompleted(Long registrationId, Long moduleId);


}
