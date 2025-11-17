package com.eam.LevelUpCorp.presentationLayer.controller;


import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.InstructorReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.service.ReportService;
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
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reportes", description = "Gestión de reportes de la plataforma")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    private final ReportService reportService;

    /**
     * Create a new report
     */
    @PostMapping
    @Operation(summary = "Crear reporte", description = "Crea un nuevo reporte en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<GeneralReportDTO> createReport(
            @Parameter(description = "Datos del reporte a crear", required = true)
            @RequestBody ReportDTO reportDTO
    ) {
        log.info("POST /api/v1/reports - Creando reporte: {}", reportDTO);
        try {
            GeneralReportDTO createdReport = reportService.createReport(reportDTO);
            log.info("Reporte creado con ID: {}", createdReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear el reporte: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get report by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID", description = "Obtiene un reporte específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<GeneralReportDTO> getReportById(
            @Parameter(description = "ID del reporte", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/reports/{} - Buscando reporte", id);
        try {
            GeneralReportDTO report = reportService.getReportById(id);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            log.warn("Reporte no encontrado con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all reports
     */
    @GetMapping
    @Operation(summary = "Listar reportes", description = "Obtiene todos los reportes registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class)))
    })
    public ResponseEntity<List<GeneralReportDTO>> getAllReports() {
        log.debug("GET /api/v1/reports - Obteniendo todos los reportes");
        List<GeneralReportDTO> reports = reportService.getAllReports();
        log.debug("Se encontraron {} reportes", reports.size());
        return ResponseEntity.ok(reports);
    }

    /**
     * Update a report
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte", description = "Actualiza los datos de un reporte existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<GeneralReportDTO> updateReport(
            @Parameter(description = "ID del reporte a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del reporte", required = true)
            @RequestBody ReportDTO reportDTO
    ) {
        log.info("PUT /api/v1/reports/{} - Actualizando reporte", id);
        try {
            GeneralReportDTO updatedReport = reportService.updateReport(id, reportDTO);
            log.info("Reporte actualizado con ID: {}", id);
            return ResponseEntity.ok(updatedReport);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar el reporte con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a report
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte existente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<Void> deleteReport(
            @Parameter(description = "ID del reporte a eliminar", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/reports/{} - Eliminando reporte", id);
        try {
            reportService.deleteReport(id);
            log.info("Reporte eliminado con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("No se encontró el reporte a eliminar con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Generate report for a specific instructor
     */
    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "Generar reporte de instructor", description = "Genera un reporte para un instructor específico con sus cursos asignados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte del instructor generado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InstructorReportDTO.class))),
            @ApiResponse(responseCode = "404", description = "Instructor no encontrado")
    })
    public ResponseEntity<InstructorReportDTO> generateInstructorReport(
            @Parameter(description = "ID del instructor", required = true)
            @PathVariable Long instructorId
    ) {
        log.info("GET /api/v1/reports/instructor/{} - Generando reporte del instructor", instructorId);
        try {
            InstructorReportDTO report = reportService.generateInstructorReport(instructorId);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            log.warn("No se pudo generar el reporte del instructor con ID {}: {}", instructorId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
