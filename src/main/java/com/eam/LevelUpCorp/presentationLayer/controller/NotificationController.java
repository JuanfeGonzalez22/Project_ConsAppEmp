package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.NotificationDTO;
import com.eam.LevelUpCorp.businessLayer.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones de notificaciones
 */
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Notificaciones", description = "Gestión de notificaciones de usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Obtener notificaciones del usuario
     */
    @GetMapping("/users/{userId}")
    @Operation(summary = "Obtener notificaciones del usuario", description = "Obtiene todas las notificaciones de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificaciones encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long userId
    ) {
        log.info("GET /api/v1/notifications/users/{} - Obteniendo notificaciones", userId);
        try {
            List<NotificationDTO> notifications = notificationService.getUserNotifications(userId);
            log.info("Se encontraron {} notificaciones para el usuario ID: {}", notifications.size(), userId);
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            log.warn("Error al obtener notificaciones para usuario ID {}: {}", userId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener notificaciones no leídas del usuario
     */
    @GetMapping("/users/{userId}/unread")
    @Operation(summary = "Obtener notificaciones no leídas", description = "Obtiene las notificaciones no leídas de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificaciones no leídas encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationDTO.class)))
    })
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long userId
    ) {
        log.debug("GET /api/v1/notifications/users/{}/unread - Obteniendo notificaciones no leídas", userId);
        try {
            List<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId);
            log.debug("Se encontraron {} notificaciones no leídas para usuario ID: {}", notifications.size(), userId);
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            log.warn("Error al obtener notificaciones no leídas para usuario ID {}: {}", userId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Marcar notificación como leída
     */
    @PutMapping("/{id}/read")
    @Operation(summary = "Marcar notificación como leída", description = "Marca una notificación específica como leída")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación marcada como leída",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<NotificationDTO> markAsRead(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable Long id
    ) {
        log.info("PUT /api/v1/notifications/{}/read - Marcando como leída", id);
        try {
            NotificationDTO updatedNotification = notificationService.markAsRead(id);
            log.info("Notificación marcada como leída - ID: {}", id);
            return ResponseEntity.ok(updatedNotification);
        } catch (RuntimeException e) {
            log.warn("Notificación no encontrada para marcar como leída - ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Contar notificaciones no leídas del usuario
     */
    @GetMapping("/users/{userId}/unread-count")
    @Operation(summary = "Contar notificaciones no leídas", description = "Obtiene el número de notificaciones no leídas de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo realizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
    })
    public ResponseEntity<Integer> getUnreadCount(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long userId
    ) {
        log.debug("GET /api/v1/notifications/users/{}/unread-count - Contando notificaciones no leídas", userId);
        try {
            int unreadCount = notificationService.getUnreadCount(userId);
            log.debug("Usuario ID: {} tiene {} notificaciones no leídas", userId, unreadCount);
            return ResponseEntity.ok(unreadCount);
        } catch (RuntimeException e) {
            log.warn("Error al contar notificaciones no leídas para usuario ID {}: {}", userId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
