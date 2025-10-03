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
@Tag(name = "Reports", description = "Management of platform reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    /**
     * Create a new report
     */
    @PostMapping
    @Operation(summary = "Create report", description = "Creates a new report in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Report created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<GeneralReportDTO> createReport(
            @Parameter(description = "Report data to create", required = true)
            @RequestBody ReportDTO reportDTO
    ) {
        log.info("POST /api/v1/reports - Creating report: {}", reportDTO);
        try {
            GeneralReportDTO createdReport = reportService.createReport(reportDTO);
            log.info("Report created with ID: {}", createdReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
        } catch (IllegalArgumentException e) {
            log.warn("Error creating report: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get report by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get report by ID", description = "Fetches a specific report by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class))),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    public ResponseEntity<GeneralReportDTO> getReportById(
            @Parameter(description = "ID of the report", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/reports/{} - Searching report", id);
        try {
            GeneralReportDTO report = reportService.getReportById(id);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            log.warn("Report not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all reports
     */
    @GetMapping
    @Operation(summary = "List reports", description = "Fetches all reports in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reports",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class)))
    })
    public ResponseEntity<List<GeneralReportDTO>> getAllReports() {
        log.debug("GET /api/v1/reports - Fetching all reports");
        List<GeneralReportDTO> reports = reportService.getAllReports();
        log.debug("Found {} reports", reports.size());
        return ResponseEntity.ok(reports);
    }

    /**
     * Update a report
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update report", description = "Updates an existing report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralReportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    public ResponseEntity<GeneralReportDTO> updateReport(
            @Parameter(description = "ID of the report to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated report data", required = true)
            @RequestBody ReportDTO reportDTO
    ) {
        log.info("PUT /api/v1/reports/{} - Updating report", id);
        try {
            GeneralReportDTO updatedReport = reportService.updateReport(id, reportDTO);
            log.info("Report updated with ID: {}", id);
            return ResponseEntity.ok(updatedReport);
        } catch (RuntimeException e) {
            log.warn("Error updating report ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a report
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete report", description = "Deletes an existing report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Report deleted"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    public ResponseEntity<Void> deleteReport(
            @Parameter(description = "ID of the report to delete", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/reports/{} - Deleting report", id);
        try {
            reportService.deleteReport(id);
            log.info("Report deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Report not found to delete ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Generate report for a specific instructor
     */
//    @GetMapping("/instructor/{instructorId}")
//    @Operation(summary = "Generate instructor report", description = "Generates a report for a specific instructor with their assigned courses")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Instructor report generated",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InstructorReportDTO.class))),
//            @ApiResponse(responseCode = "404", description = "Instructor not found")
//    })
//    public ResponseEntity<InstructorReportDTO> generateInstructorReport(
//            @Parameter(description = "ID of the instructor", required = true)
//            @PathVariable Long instructorId
//    ) {
//        log.info("GET /api/v1/reports/instructor/{} - Generating instructor report", instructorId);
//        try {
//            InstructorReportDTO report = reportService.generateInstructorReport(instructorId);
//            return ResponseEntity.ok(report);
//        } catch (RuntimeException e) {
//            log.warn("Instructor report could not be generated for ID {}: {}", instructorId, e.getMessage());
//            return ResponseEntity.notFound().build();
//        }
//    }





}
