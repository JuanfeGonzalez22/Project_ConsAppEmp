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
@Tag(name = "Registrations", description = "Manage user registrations in the platform")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(summary = "Create registration", description = "Register a new user in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Registration created",
            content = @Content(schema = @Schema(implementation = RegistrationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    // Create a new user registration.
    @PostMapping
    public ResponseEntity<RegistrationDTO> create(@RequestBody RegistrationDTO dto) {
        log.info("POST /api/v1/registrations - Create registration");
        try {
            RegistrationDTO createdRegistration = registrationService.createRegistration(dto);
            return ResponseEntity.status(201).body(createdRegistration);
        } catch (IllegalArgumentException e) {
            log.warn("Error creating registration: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get registration by ID", description = "Retrieve a registration by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registration found",
            content = @Content(schema = @Schema(implementation = RegistrationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Registration not found", content = @Content)
    })
    // Retrieve a specific registration by ID.
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getOne(
        @Parameter(description = "ID of the registration to retrieve", required = true)
        @PathVariable Long id) {
        log.debug("GET /api/v1/registrations/{} - Get registration", id);
        try {
            RegistrationDTO registration = registrationService.getRegistration(id);
            return ResponseEntity.ok(registration);
        } catch (RuntimeException e) {
            log.warn("Registration with ID {} not found: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all registrations", description = "Retrieve all registrations")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of registrations",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegistrationDTO.class))))
    })
    // Retrieve all user registrations.
    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> getAll() {
        log.debug("GET /api/v1/registrations - Get all registrations");
        List<RegistrationDTO> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @Operation(summary = "Update registration", description = "Update an existing registration by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registration updated",
            content = @Content(schema = @Schema(implementation = RegistrationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Registration not found", content = @Content)
    })
    // Update an existing registration by ID.
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDTO> update(
            @Parameter(description = "ID of the registration to update", required = true)
            @PathVariable Long id,
            @RequestBody RegistrationDTO dto) {
        log.info("PUT /api/v1/registrations/{} - Update registration", id);
        try {
            RegistrationDTO updatedRegistration = registrationService.updateRegistration(id, dto);
            return ResponseEntity.ok(updatedRegistration);
        } catch (RuntimeException e) {
            log.warn("Error updating registration ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete registration", description = "Delete a registration by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Registration deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Registration not found", content = @Content)
    })
    // Delete a registration by ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID of the registration to delete", required = true)
        @PathVariable Long id) {
        log.info("DELETE /api/v1/registrations/{} - Delete registration", id);
        try {
            registrationService.deleteRegistration(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Error deleting registration ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
