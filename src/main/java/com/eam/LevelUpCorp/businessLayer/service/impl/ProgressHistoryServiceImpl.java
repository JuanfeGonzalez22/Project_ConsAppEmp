package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.ProgressHistoryDTO;
import com.eam.LevelUpCorp.businessLayer.service.NotificationService;
import com.eam.LevelUpCorp.businessLayer.service.ProgressHistoryService;
import com.eam.LevelUpCorp.persistenceLayer.dao.ProgressHistoryDAO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.ModuleEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.RegistrationEntity;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.ModuleRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProgressHistoryServiceImpl implements ProgressHistoryService {



    private final ProgressHistoryDAO progressHistoryDAO;
    private final RegistrationRepository registrationRepository;
    private final ModuleRepository moduleRepository;
    private final NotificationService notificationService;
    private final CourseRepository courseRepository;





    @Override
    public ProgressHistoryDTO markModuleAsCompleted(Long registrationId, Long moduleId, LocalTime timeDedicated) {
        log.info("Marking module as completed - Registration: {}, Module: {}", registrationId, moduleId);

        // Verificar que la inscripción existe
        RegistrationEntity registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> {
                    log.warn("Registration not found: {}", registrationId);
                    return new RuntimeException("Registration not found with ID: " + registrationId);
                });

        // Verificar que el módulo existe
        ModuleEntity module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> {
                    log.warn("Module not found: {}", moduleId);
                    return new RuntimeException("Module not found with ID: " + moduleId);
                });

        // Calcular nuevo progreso
        Long courseId = registration.getCourseId();
        int totalModules = moduleRepository.countByCourseId(courseId);
        int completedModules = progressHistoryDAO.countCompletedModules(registrationId, courseId);
        double newProgress = ((completedModules + 1.0) / totalModules) * 100.0;

        // Crear DTO para guardar
        ProgressHistoryDTO progressDTO = new ProgressHistoryDTO();
        progressDTO.setUserId(registration.getUserId());
        progressDTO.setCourseId(courseId);
        progressDTO.setModuleId(moduleId);
        progressDTO.setRegistrationId(registrationId);
        progressDTO.setTimeDedicated(timeDedicated);
        progressDTO.setStatus("COMPLETED");
        progressDTO.setModuleProgress(100.0);
        progressDTO.setEvaluationAttempts(0); // Por defecto

        // Guardar en historial
        ProgressHistoryDTO savedProgress = progressHistoryDAO.save(progressDTO);

        // Actualizar progreso en registration
        registration.setProgress(newProgress);
        if (newProgress >= 100.0) {
            registration.setStatus("COMPLETED");
        }
        registrationRepository.save(registration);

        createProgressNotification(registration.getUserId(), courseId, newProgress);

        log.info("Module marked as completed successfully - Progress: {}%", newProgress);
        return savedProgress;
    }

    @Override
    @Transactional(readOnly = true)
    public ProgressHistoryDTO getCurrentProgress(Long registrationId) {
        log.info("Getting current progress for registration: {}", registrationId);

        RegistrationEntity registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> {
                    log.warn("Registration not found: {}", registrationId);
                    return new RuntimeException("Registration not found with ID: " + registrationId);
                });

        // Calcular métricas actuales
        Long courseId = registration.getCourseId();
        LocalTime totalTime = progressHistoryDAO.calculateTotalTimeDedicated(registrationId, courseId);
        int evaluationAttempts = progressHistoryDAO.countCompletedModules(registrationId, courseId);

        // Crear DTO con progreso actual
        ProgressHistoryDTO currentProgress = new ProgressHistoryDTO();
        currentProgress.setUserId(registration.getUserId());
        currentProgress.setCourseId(courseId);
        currentProgress.setModuleId(currentProgress.getModuleId()); // Podemos mejorarlo después
        currentProgress.setTimeDedicated(totalTime);
        currentProgress.setStatus(registration.getStatus());
        currentProgress.setModuleProgress(registration.getProgress());
        currentProgress.setEvaluationAttempts(evaluationAttempts);

        log.info("Current progress retrieved - User: {}, Progress: {}%",
                currentProgress.getUserId(), currentProgress.getModuleProgress());
        return currentProgress;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgressHistoryDTO> getProgressHistory(Long registrationId) {
        log.info("Getting progress history for registration: {}", registrationId);

        RegistrationEntity registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> {
                    log.warn("Registration not found: {}", registrationId);
                    return new RuntimeException("Registration not found with ID: " + registrationId);
                });

        List<ProgressHistoryDTO> history = progressHistoryDAO.findByRegistrationNadCourse(
                registrationId, registration.getCourseId());

        if (history.isEmpty()) {
            log.warn("No progress history found for registration: {}", registrationId);
            throw new RuntimeException("No progress history available");
        }

        log.info("Found {} progress records", history.size());
        return history;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isModuleCompleted(Long registrationId, Long moduleId) {
        log.info("Checking if module is completed - Registration: {}, Module: {}", registrationId, moduleId);
        boolean completed = progressHistoryDAO.isModuleCompleted(registrationId, moduleId);
        log.info("Module completed check - Result: {}", completed);
        return completed;
    }

    private void createProgressNotification(Long userId, Long courseId, double progress) {

        try {
            CourseEntity course = courseRepository.findById(courseId).orElse(null);

            String courseTitle = course != null ? course.getTitle() : "Curso";

            String message = "";
            String type = "";

            if (progress >= 100.0) {

                message = "Felicidades has completado un curso: " + courseTitle;
                type = "COURSE_COMPLETED";

            } else if (progress >= 80.0) {

                message = "¡Ya casi terminas! Progreso en:  " + courseTitle + (int)progress + "%";
                type = "PROGRESS_MILESTONE";

            } else if (progress >= 50.0 ) {

                message = "¡Vas por la mitad! Progreso en:  " + courseTitle + ": " + (int)progress + "%";
                type = "PROGRESS_MILESTONE";

            } else if (progress >= 25.0) {
                message = "Buen comienzo en:  " + courseTitle + ". Progreso: " + (int)progress + "%";
                type = "PROGRESS_UPDATE";
            }

            if (!message.isEmpty()) {

                notificationService.createProgressNotification(userId, type, message);

                log.info("Notificacion creada por usuario {} - Progreso: {}%", userId, progress);
            }

        } catch (Exception e) {
            log.warn("Error creando notificacion de progreso para usuario {}: {}", userId, e.getMessage());
        }

    }




}
