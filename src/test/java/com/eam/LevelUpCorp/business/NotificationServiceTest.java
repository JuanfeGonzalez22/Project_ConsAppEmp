package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.NotificationDTO;
import com.eam.LevelUpCorp.businessLayer.service.NotificationService;
import com.eam.LevelUpCorp.businessLayer.service.impl.NotificationServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.NotificationValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.NotificationDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para NotificationServiceImpl
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("NotificationService - Unit Tests")
public class NotificationServiceTest {

    @Mock
    private NotificationDAO notificationDAO;

    @Mock
    private NotificationValidate notificationValidate;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationDTO validNotificationDTO;
    private Long validUserId;
    private Long validNotificationId;

    @BeforeEach
    void setUp() {
        validUserId = 1L;
        validNotificationId = 1L;

        validNotificationDTO = new NotificationDTO(
                validNotificationId,
                validUserId,
                "PROGRESS_UPDATE",
                "¡Has completado el curso de Spring Boot!",
                LocalDate.now(),
                "UNREAD"
        );
    }

    @Test
    @DisplayName("GET USER NOTIFICATIONS - Usuario con notificaciones debe retornar lista")
    void getUserNotifications_UserWithNotifications_ShouldReturnList() {
        List<NotificationDTO> expectedNotifications = Arrays.asList(
                validNotificationDTO,
                new NotificationDTO(2L, validUserId, "SYSTEM", "Nueva actualización disponible", LocalDate.now(), "UNREAD")
        );

        doNothing().when(notificationValidate).validateUserSearch(validUserId);
        when(notificationDAO.findUnreadByUserId(validUserId)).thenReturn(expectedNotifications);

        List<NotificationDTO> result = notificationService.getUserNotifications(validUserId);

        assertThat(result).isNotNull().hasSize(2);
        verify(notificationValidate).validateUserSearch(validUserId);
        verify(notificationDAO).findUnreadByUserId(validUserId);
    }

    @Test
    @DisplayName("GET USER NOTIFICATIONS - UserId null debe lanzar IllegalArgumentException")
    void getUserNotifications_NullUserId_ShouldThrowException() {
        Long nullUserId = null;

        doThrow(new IllegalArgumentException("User ID es obligatorio"))
                .when(notificationValidate).validateUserSearch(nullUserId);

        assertThatThrownBy(() -> notificationService.getUserNotifications(nullUserId))
                .isInstanceOf(IllegalArgumentException.class);

        verify(notificationDAO, never()).findUnreadByUserId(anyLong());
    }

    @Test
    @DisplayName("CREATE PROGRESS NOTIFICATION - Datos válidos debe crear notificación")
    void createProgressNotification_ValidData_ShouldCreateNotification() {
        String type = "PROGRESS_UPDATE";
        String message = "¡Has avanzado al siguiente nivel!";

        NotificationDTO savedNotification = new NotificationDTO(1L, validUserId, type, message, LocalDate.now(), "UNREAD");

        doNothing().when(notificationValidate).validateProgressNotification(validUserId, type, message);
        when(notificationDAO.save(any(NotificationDTO.class))).thenReturn(savedNotification);

        notificationService.createProgressNotification(validUserId, type, message);

        verify(notificationValidate).validateProgressNotification(validUserId, type, message);
        verify(notificationDAO).save(any(NotificationDTO.class));
    }

    @Test
    @DisplayName("CREATE PROGRESS NOTIFICATION - UserId null debe lanzar excepción")
    void createProgressNotification_NullUserId_ShouldThrowException() {
        String type = "PROGRESS_UPDATE";
        String message = "Test message";

        doThrow(new IllegalArgumentException("User ID es obligatorio"))
                .when(notificationValidate).validateProgressNotification(null, type, message);

        assertThatThrownBy(() -> notificationService.createProgressNotification(null, type, message))
                .isInstanceOf(IllegalArgumentException.class);

        verify(notificationDAO, never()).save(any(NotificationDTO.class));
    }
}