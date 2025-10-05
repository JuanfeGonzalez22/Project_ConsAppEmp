package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import com.eam.LevelUpCorp.businessLayer.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/registrations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Registros", description = "Gestión de registros de usuarios en la plataforma")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(summary = "Crear registro", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro creado",
                    content = @Content(schema = @Schema(implementation = RegistrationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    // Crear un nuevo registro de usuario.
    @PostMapping
    public ResponseEntity<RegistrationDTO> create(@RequestBody RegistrationDTO dto) {
        log.info("POST /api/v1/registrations - Crear registro");
        try {
            RegistrationDTO createdRegistration = registrationService.createRegistration(dto);
            return ResponseEntity.status(201).body(createdRegistration);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear el registro: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener registro por ID", description = "Obtiene un registro específico según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(schema = @Schema(implementation = RegistrationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    // Obtener un registro específico por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getOne(
            @Parameter(description = "ID del registro a obtener", required = true)
            @PathVariable Long id) {
        log.debug("GET /api/v1/registrations/{} - Obtener registro", id);
        try {
            RegistrationDTO registration = registrationService.getRegistration(id);
            return ResponseEntity.ok(registration);
        } catch (RuntimeException e) {
            log.warn("Registro con ID {} no encontrado: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar registros", description = "Obtiene todos los registros de usuarios del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de registros obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegistrationDTO.class))))
    })
    // Obtener todos los registros de usuarios.
    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> getAll() {
        log.debug("GET /api/v1/registrations - Obtener todos los registros");
        List<RegistrationDTO> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @Operation(summary = "Actualizar registro", description = "Actualiza un registro existente según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = RegistrationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    // Actualizar un registro existente por su ID.
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDTO> update(
            @Parameter(description = "ID del registro a actualizar", required = true)
            @PathVariable Long id,
            @RequestBody RegistrationDTO dto) {
        log.info("PUT /api/v1/registrations/{} - Actualizar registro", id);
        try {
            RegistrationDTO updatedRegistration = registrationService.updateRegistration(id, dto);
            return ResponseEntity.ok(updatedRegistration);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar el registro con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar registro", description = "Elimina un registro según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    // Eliminar un registro por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del registro a eliminar", required = true)
            @PathVariable Long id) {
        log.info("DELETE /api/v1/registrations/{} - Eliminar registro", id);
        try {
            registrationService.deleteRegistration(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Error al eliminar el registro con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
