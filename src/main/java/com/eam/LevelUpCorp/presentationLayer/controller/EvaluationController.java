package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;
import com.eam.LevelUpCorp.businessLayer.service.EvaluationService;
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
@RequestMapping("/api/v1/evaluations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Evaluations", description = "Evaluations management")
@CrossOrigin(origins = "*")
public class EvaluationController {


    private final EvaluationService evaluationService;


    /*
        Create an evaluation.
     */
    @PostMapping
    @Operation(summary = "Create evaluatión", description = "Create a new evaluatión in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created evaluation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dates inválid")
    })
    public ResponseEntity<EvaluationDTO> createEvaluation(
            @Parameter(description = "Evaluations data to be created", required = true)
            @RequestBody EvaluationDTO evaluationDTO
    ) {
        log.info("POST /api/v1/evaluations - Creating evaluatión: {}", evaluationDTO.getTitle());
        try {
            EvaluationDTO created = evaluationService.create(evaluationDTO);
            log.info("Evaluatión created con ID: {}", created);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Error create evaluation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /*
        Get an evaluation by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get evaluatión by ID", description = "Get an specífic evaluatión by your ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluatión found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Evaluatión not found")
    })
    public ResponseEntity<EvaluationDTO> getById(
            @Parameter(description = "ID of the  evaluatión", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/evaluations/{} - Search evaluatión", id);
        try {
            EvaluationDTO evaluation = evaluationService.getById(id);
            return ResponseEntity.ok(evaluation);
        } catch (RuntimeException e) {
            log.warn("Evaluatión not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /*
     Get list of evaluations.
 */
    @GetMapping
    @Operation(summary = "List evaluations", description = "Gets all evaluations in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of evaluations",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class)))
    })
    public ResponseEntity<List<EvaluationDTO>> findAll() {
        log.debug("GET /api/v1/evaluations - Getting all evaluations");
        List<EvaluationDTO> evaluations = evaluationService.findAll();
        log.debug("Found {} evaluations", evaluations.size());
        return ResponseEntity.ok(evaluations);
    }

    /*
        Get list of evaluations by module.
    */
    @GetMapping("/module/{moduleId}")
    @Operation(summary = "List evaluations by module", description = "Gets all evaluations associated with a module")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of module evaluations",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "404", description = "No evaluations found")
    })
    public ResponseEntity<List<EvaluationDTO>> getByModuleId(
            @Parameter(description = "Module ID", required = true)
            @PathVariable Long moduleId
    ) {
        log.debug("GET /api/v1/evaluations/module/{} - Searching evaluations by module", moduleId);
        try {
            List<EvaluationDTO> evaluations = evaluationService.getEvaluationByModuleId(moduleId);
            return ResponseEntity.ok(evaluations);
        } catch (RuntimeException e) {
            log.warn("No evaluations found for module ID: {}", moduleId);
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Update an evaluation.
    */
    @PutMapping("/{id}")
    @Operation(summary = "Update evaluation", description = "Updates an existing evaluation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluation updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    public ResponseEntity<EvaluationDTO> update(
            @Parameter(description = "ID of the evaluation to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Evaluation data to update", required = true)
            @RequestBody EvaluationDTO evaluationDTO
    ) {
        log.info("PUT /api/v1/evaluations/{} - Updating evaluation", id);
        try {
            EvaluationDTO updated = evaluationService.update(id, evaluationDTO);
            log.info("Evaluation updated with ID: {}", id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Error updating evaluation ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Delete an evaluation.
    */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete evaluation", description = "Deletes an existing evaluation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evaluation deleted"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the evaluation to delete", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/evaluations/{} - Deleting evaluation", id);
        try {
            evaluationService.deleteById(id);
            log.info("Evaluation deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Evaluation not found to delete ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


}