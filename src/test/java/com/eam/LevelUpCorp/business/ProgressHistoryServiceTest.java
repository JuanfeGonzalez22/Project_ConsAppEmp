package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.ProgressHistoryDTO;
import com.eam.LevelUpCorp.businessLayer.service.NotificationService;
import com.eam.LevelUpCorp.businessLayer.service.ProgressHistoryService;
import com.eam.LevelUpCorp.businessLayer.service.impl.ProgressHistoryServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.ProgressHistoryValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ProgressHistoryDAO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.ModuleEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.RegistrationEntity;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.ModuleRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProgressHistoryService - Unit Tests")
public class ProgressHistoryServiceTest {

    @Mock
    private ProgressHistoryDAO progressHistoryDAO;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ProgressHistoryValidate progressHistoryValidate;

    @InjectMocks
    private ProgressHistoryServiceImpl progressHistoryService;

    private Long validRegistrationId;
    private Long validModuleId;
    private Long validCourseId;
    private Long validUserId;
    private RegistrationEntity validRegistration;
    private ModuleEntity validModule;
    private CourseEntity validCourse;
    private ProgressHistoryDTO validProgressHistoryDTO;

    @BeforeEach
    void setUp() {
        validRegistrationId = 1L;
        validModuleId = 10L;
        validCourseId = 5L;
        validUserId = 100L;

        validRegistration = new RegistrationEntity();
        validRegistration.setId(validRegistrationId);
        validRegistration.setUserId(validUserId);
        validRegistration.setCourseId(validCourseId);
        validRegistration.setProgress(50.0);
        validRegistration.setStatus("IN_PROGRESS");

        validModule = new ModuleEntity();
        validModule.setId(validModuleId);
        validModule.setCourseId(validCourseId);
        validModule.setTitle("Spring Boot Basics");

        validCourse = new CourseEntity();
        validCourse.setId(validCourseId);
        validCourse.setTitle("Spring Boot Advanced");

        validProgressHistoryDTO = new ProgressHistoryDTO();
        validProgressHistoryDTO.setUserId(validUserId);
        validProgressHistoryDTO.setCourseId(validCourseId);
        validProgressHistoryDTO.setModuleId(validModuleId);
        validProgressHistoryDTO.setRegistrationId(validRegistrationId);
        validProgressHistoryDTO.setTimeDedicated(LocalTime.of(1, 30));
        validProgressHistoryDTO.setStatus("COMPLETED");
        validProgressHistoryDTO.setModuleProgress(100.0);
        validProgressHistoryDTO.setEvaluationAttempts(0);
    }


    @Test
    @DisplayName("VALIDATION - RegistrationId null debe lanzar IllegalArgumentException")
    void markModuleAsCompleted_NullRegistrationId_ShouldThrowException() {
        LocalTime timeDedicated = LocalTime.of(1, 0);

        doThrow(new IllegalArgumentException("Registration ID es obligatorio"))
                .when(progressHistoryValidate).validateMarkModuleAsCompleted(null, validModuleId, timeDedicated);

        assertThatThrownBy(() -> progressHistoryService.markModuleAsCompleted(null, validModuleId, timeDedicated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Registration ID es obligatorio");

        verify(registrationRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("VALIDATION - ModuleId null debe lanzar IllegalArgumentException")
    void markModuleAsCompleted_NullModuleId_ShouldThrowException() {
        LocalTime timeDedicated = LocalTime.of(1, 0);

        doThrow(new IllegalArgumentException("Module ID es obligatorio"))
                .when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, null, timeDedicated);

        assertThatThrownBy(() -> progressHistoryService.markModuleAsCompleted(validRegistrationId, null, timeDedicated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Module ID es obligatorio");

        verify(registrationRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("VALIDATION - TimeDedicated null debe lanzar IllegalArgumentException")
    void markModuleAsCompleted_NullTimeDedicated_ShouldThrowException() {
        doThrow(new IllegalArgumentException("El tiempo dedicado no puede ser nulo"))
                .when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, validModuleId, null);

        assertThatThrownBy(() -> progressHistoryService.markModuleAsCompleted(validRegistrationId, validModuleId, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El tiempo dedicado no puede ser nulo");

        verify(registrationRepository, never()).findById(anyLong());
    }


    @Test
    @DisplayName("MARK MODULE COMPLETED - Datos válidos debe marcar módulo como completado")
    void markModuleAsCompleted_ValidData_ShouldReturnProgressHistory() {
        LocalTime timeDedicated = LocalTime.of(2, 15);
        int totalModules = 4;
        int completedModules = 1;

        doNothing().when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, validModuleId, timeDedicated);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(moduleRepository.findById(validModuleId)).thenReturn(Optional.of(validModule));
        when(moduleRepository.countByCourseId(validCourseId)).thenReturn(totalModules);
        when(progressHistoryDAO.countCompletedModules(validRegistrationId, validCourseId)).thenReturn(completedModules);
        when(progressHistoryDAO.save(any(ProgressHistoryDTO.class))).thenReturn(validProgressHistoryDTO);
        when(courseRepository.findById(validCourseId)).thenReturn(Optional.of(validCourse));
        doNothing().when(notificationService).createProgressNotification(anyLong(), anyString(), anyString());

        ProgressHistoryDTO result = progressHistoryService.markModuleAsCompleted(
                validRegistrationId, validModuleId, timeDedicated);

        assertThat(result).isNotNull();
        assertThat(result.getModuleId()).isEqualTo(validModuleId);
        assertThat(result.getStatus()).isEqualTo("COMPLETED");

        verify(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, validModuleId, timeDedicated);
        verify(registrationRepository).findById(validRegistrationId);
        verify(moduleRepository).findById(validModuleId);
        verify(progressHistoryDAO).save(any(ProgressHistoryDTO.class));
        verify(registrationRepository).save(any(RegistrationEntity.class));
    }

    @Test
    @DisplayName("MARK MODULE COMPLETED - Registration inexistente debe lanzar RuntimeException")
    void markModuleAsCompleted_NonExistentRegistration_ShouldThrowException() {
        Long nonExistentId = 999L;
        LocalTime timeDedicated = LocalTime.of(1, 0);

        doNothing().when(progressHistoryValidate).validateMarkModuleAsCompleted(nonExistentId, validModuleId, timeDedicated);
        when(registrationRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> progressHistoryService.markModuleAsCompleted(
                nonExistentId, validModuleId, timeDedicated))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Registration not found with ID: " + nonExistentId);

        verify(progressHistoryDAO, never()).save(any(ProgressHistoryDTO.class));
    }

    @Test
    @DisplayName("MARK MODULE COMPLETED - Module inexistente debe lanzar RuntimeException")
    void markModuleAsCompleted_NonExistentModule_ShouldThrowException() {
        Long nonExistentModuleId = 999L;
        LocalTime timeDedicated = LocalTime.of(1, 0);

        doNothing().when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, nonExistentModuleId, timeDedicated);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(moduleRepository.findById(nonExistentModuleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> progressHistoryService.markModuleAsCompleted(
                validRegistrationId, nonExistentModuleId, timeDedicated))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Module not found with ID: " + nonExistentModuleId);

        verify(progressHistoryDAO, never()).save(any(ProgressHistoryDTO.class));
    }

    @Test
    @DisplayName("MARK MODULE COMPLETED - Progreso 100% debe actualizar status a COMPLETED")
    void markModuleAsCompleted_Progress100_ShouldUpdateStatusToCompleted() {
        LocalTime timeDedicated = LocalTime.of(3, 0);
        int totalModules = 4;
        int completedModules = 3;

        doNothing().when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, validModuleId, timeDedicated);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(moduleRepository.findById(validModuleId)).thenReturn(Optional.of(validModule));
        when(moduleRepository.countByCourseId(validCourseId)).thenReturn(totalModules);
        when(progressHistoryDAO.countCompletedModules(validRegistrationId, validCourseId)).thenReturn(completedModules);
        when(progressHistoryDAO.save(any(ProgressHistoryDTO.class))).thenReturn(validProgressHistoryDTO);
        when(courseRepository.findById(validCourseId)).thenReturn(Optional.of(validCourse));
        doNothing().when(notificationService).createProgressNotification(anyLong(), anyString(), anyString());

        ProgressHistoryDTO result = progressHistoryService.markModuleAsCompleted(
                validRegistrationId, validModuleId, timeDedicated);

        assertThat(result).isNotNull();
        verify(registrationRepository).save(argThat(registration ->
                registration.getProgress() == 100.0));
    }


    @Test
    @DisplayName("GET CURRENT PROGRESS - Registration existente debe retornar progreso actual")
    void getCurrentProgress_ExistingRegistration_ShouldReturnCurrentProgress() {
        LocalTime totalTime = LocalTime.of(5, 30);
        int completedModules = 3;

        doNothing().when(progressHistoryValidate).validateGetCurrentProgress(validRegistrationId);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(progressHistoryDAO.calculateTotalTimeDedicated(validRegistrationId, validCourseId)).thenReturn(totalTime);
        when(progressHistoryDAO.countCompletedModules(validRegistrationId, validCourseId)).thenReturn(completedModules);

        ProgressHistoryDTO result = progressHistoryService.getCurrentProgress(validRegistrationId);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(validUserId);
        assertThat(result.getCourseId()).isEqualTo(validCourseId);
        assertThat(result.getTimeDedicated()).isEqualTo(totalTime);
        assertThat(result.getEvaluationAttempts()).isEqualTo(completedModules);

        verify(progressHistoryValidate).validateGetCurrentProgress(validRegistrationId);
        verify(registrationRepository).findById(validRegistrationId);
        verify(progressHistoryDAO).calculateTotalTimeDedicated(validRegistrationId, validCourseId);
        verify(progressHistoryDAO).countCompletedModules(validRegistrationId, validCourseId);
    }

    @Test
    @DisplayName("GET CURRENT PROGRESS - Registration inexistente debe lanzar RuntimeException")
    void getCurrentProgress_NonExistentRegistration_ShouldThrowException() {
        Long nonExistentId = 999L;

        doNothing().when(progressHistoryValidate).validateGetCurrentProgress(nonExistentId);
        when(registrationRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> progressHistoryService.getCurrentProgress(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Registration not found with ID: " + nonExistentId);

        verify(progressHistoryDAO, never()).calculateTotalTimeDedicated(anyLong(), anyLong());
    }


    @Test
    @DisplayName("GET PROGRESS HISTORY - Con historial existente debe retornar lista")
    void getProgressHistory_WithHistory_ShouldReturnList() {
        ProgressHistoryDTO progress2 = new ProgressHistoryDTO();
        progress2.setUserId(validUserId);
        progress2.setCourseId(validCourseId);
        progress2.setModuleId(11L);
        progress2.setRegistrationId(validRegistrationId);
        progress2.setTimeDedicated(LocalTime.of(0, 45));
        progress2.setStatus("IN_PROGRESS");
        progress2.setModuleProgress(75.0);
        progress2.setEvaluationAttempts(1);

        List<ProgressHistoryDTO> history = Arrays.asList(validProgressHistoryDTO, progress2);

        doNothing().when(progressHistoryValidate).validateGetProgressHistory(validRegistrationId);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(progressHistoryDAO.findByRegistrationNadCourse(validRegistrationId, validCourseId)).thenReturn(history);

        List<ProgressHistoryDTO> result = progressHistoryService.getProgressHistory(validRegistrationId);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getModuleId()).isEqualTo(validModuleId);
        assertThat(result.get(1).getModuleId()).isEqualTo(11L);

        verify(progressHistoryValidate).validateGetProgressHistory(validRegistrationId);
        verify(registrationRepository).findById(validRegistrationId);
        verify(progressHistoryDAO).findByRegistrationNadCourse(validRegistrationId, validCourseId);
    }

    @Test
    @DisplayName("GET PROGRESS HISTORY - Sin historial debe lanzar RuntimeException")
    void getProgressHistory_EmptyHistory_ShouldThrowException() {
        doNothing().when(progressHistoryValidate).validateGetProgressHistory(validRegistrationId);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(progressHistoryDAO.findByRegistrationNadCourse(validRegistrationId, validCourseId)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> progressHistoryService.getProgressHistory(validRegistrationId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No progress history available");

        verify(progressHistoryDAO).findByRegistrationNadCourse(validRegistrationId, validCourseId);
    }


    @Test
    @DisplayName("IS MODULE COMPLETED - Módulo completado debe retornar true")
    void isModuleCompleted_CompletedModule_ShouldReturnTrue() {
        doNothing().when(progressHistoryValidate).validateIsModuleCompleted(validRegistrationId, validModuleId);
        when(progressHistoryDAO.isModuleCompleted(validRegistrationId, validModuleId)).thenReturn(true);

        boolean result = progressHistoryService.isModuleCompleted(validRegistrationId, validModuleId);

        assertThat(result).isTrue();
        verify(progressHistoryValidate).validateIsModuleCompleted(validRegistrationId, validModuleId);
        verify(progressHistoryDAO).isModuleCompleted(validRegistrationId, validModuleId);
    }

    @Test
    @DisplayName("IS MODULE COMPLETED - Módulo no completado debe retornar false")
    void isModuleCompleted_NotCompletedModule_ShouldReturnFalse() {
        doNothing().when(progressHistoryValidate).validateIsModuleCompleted(validRegistrationId, validModuleId);
        when(progressHistoryDAO.isModuleCompleted(validRegistrationId, validModuleId)).thenReturn(false);

        boolean result = progressHistoryService.isModuleCompleted(validRegistrationId, validModuleId);

        assertThat(result).isFalse();
        verify(progressHistoryValidate).validateIsModuleCompleted(validRegistrationId, validModuleId);
        verify(progressHistoryDAO).isModuleCompleted(validRegistrationId, validModuleId);
    }


    @Test
    @DisplayName("NOTIFICATION - Progreso 25% debe crear notificación de inicio")
    void markModuleAsCompleted_Progress25_ShouldCreateStartNotification() {
        LocalTime timeDedicated = LocalTime.of(1, 0);
        int totalModules = 4;
        int completedModules = 0;

        doNothing().when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, validModuleId, timeDedicated);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(moduleRepository.findById(validModuleId)).thenReturn(Optional.of(validModule));
        when(moduleRepository.countByCourseId(validCourseId)).thenReturn(totalModules);
        when(progressHistoryDAO.countCompletedModules(validRegistrationId, validCourseId)).thenReturn(completedModules);
        when(progressHistoryDAO.save(any(ProgressHistoryDTO.class))).thenReturn(validProgressHistoryDTO);
        when(courseRepository.findById(validCourseId)).thenReturn(Optional.of(validCourse));
        doNothing().when(notificationService).createProgressNotification(anyLong(), anyString(), anyString());

        progressHistoryService.markModuleAsCompleted(validRegistrationId, validModuleId, timeDedicated);

        verify(notificationService).createProgressNotification(
                eq(validUserId),
                eq("PROGRESS_UPDATE"),
                contains("Buen comienzo")
        );
    }


    @Test
    @DisplayName("EDGE CASE - Course no encontrado en notificación debe manejarse correctamente")
    void markModuleAsCompleted_CourseNotFound_ShouldHandleGracefully() {
        LocalTime timeDedicated = LocalTime.of(1, 0);
        int totalModules = 4;
        int completedModules = 1;

        doNothing().when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, validModuleId, timeDedicated);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(moduleRepository.findById(validModuleId)).thenReturn(Optional.of(validModule));
        when(moduleRepository.countByCourseId(validCourseId)).thenReturn(totalModules);
        when(progressHistoryDAO.countCompletedModules(validRegistrationId, validCourseId)).thenReturn(completedModules);
        when(progressHistoryDAO.save(any(ProgressHistoryDTO.class))).thenReturn(validProgressHistoryDTO);
        when(courseRepository.findById(validCourseId)).thenReturn(Optional.empty());
        doNothing().when(notificationService).createProgressNotification(anyLong(), anyString(), anyString());

        ProgressHistoryDTO result = progressHistoryService.markModuleAsCompleted(
                validRegistrationId, validModuleId, timeDedicated);

        assertThat(result).isNotNull();
        verify(notificationService).createProgressNotification(anyLong(), anyString(), contains("Curso"));
    }

    @Test
    @DisplayName("EDGE CASE - Error en notificación no debe afectar flujo principal")
    void markModuleAsCompleted_NotificationError_ShouldNotAffectMainFlow() {
        LocalTime timeDedicated = LocalTime.of(1, 0);
        int totalModules = 4;
        int completedModules = 1;

        doNothing().when(progressHistoryValidate).validateMarkModuleAsCompleted(validRegistrationId, validModuleId, timeDedicated);
        when(registrationRepository.findById(validRegistrationId)).thenReturn(Optional.of(validRegistration));
        when(moduleRepository.findById(validModuleId)).thenReturn(Optional.of(validModule));
        when(moduleRepository.countByCourseId(validCourseId)).thenReturn(totalModules);
        when(progressHistoryDAO.countCompletedModules(validRegistrationId, validCourseId)).thenReturn(completedModules);
        when(progressHistoryDAO.save(any(ProgressHistoryDTO.class))).thenReturn(validProgressHistoryDTO);
        when(courseRepository.findById(validCourseId)).thenReturn(Optional.of(validCourse));
        doThrow(new RuntimeException("Notification service error"))
                .when(notificationService).createProgressNotification(anyLong(), anyString(), anyString());

        assertThatCode(() -> progressHistoryService.markModuleAsCompleted(
                validRegistrationId, validModuleId, timeDedicated))
                .doesNotThrowAnyException();

        verify(progressHistoryDAO).save(any(ProgressHistoryDTO.class));
    }
}