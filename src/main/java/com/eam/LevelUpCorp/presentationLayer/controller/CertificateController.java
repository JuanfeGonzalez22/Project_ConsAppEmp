package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import com.eam.LevelUpCorp.businessLayer.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Certificates", description = "Gestión de certificados")
@CrossOrigin(origins = "*")
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping
    @Operation(summary = "Crear certificado", description = "Crea un nuevo certificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Certificado creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CertificateDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CertificateDTO> createCertificate(
            @Parameter(description = "Datos del certificado", required = true)
            @RequestBody CertificateDTO certificateDTO
    ) {
        log.info("POST /api/v1/certificates - Creando certificado para usuario {}", certificateDTO.getUserId());
        try {
            CertificateDTO created = certificateService.createCertificate(certificateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear certificado: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener certificado por ID", description = "Obtiene un certificado por su ID")
    public ResponseEntity<CertificateDTO> getCertificate(
            @Parameter(description = "ID del certificado", required = true)
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(certificateService.getCertificate(id));
        } catch (RuntimeException e) {
            log.warn("Certificado no encontrado con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar certificados", description = "Obtiene todos los certificados")
    public ResponseEntity<List<CertificateDTO>> getCertificates() {
        return ResponseEntity.ok(certificateService.getCertificates());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar certificado", description = "Actualiza un certificado existente")
    public ResponseEntity<CertificateDTO> updateCertificate(
            @Parameter(description = "ID del certificado", required = true)
            @PathVariable Long id,
            @RequestBody CertificateDTO certificateDTO
    ) {
        try {
            CertificateDTO updated = certificateService.updateCertificate(id, certificateDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar certificado con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar certificado", description = "Elimina un certificado por ID")
    public ResponseEntity<Void> deleteCertificate(
            @Parameter(description = "ID del certificado", required = true)
            @PathVariable Long id
    ) {
        try {
            certificateService.deleteCertificate(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Certificado no encontrado para eliminar ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
