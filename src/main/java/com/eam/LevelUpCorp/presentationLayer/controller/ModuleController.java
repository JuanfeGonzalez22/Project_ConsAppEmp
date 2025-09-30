package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.ModuleDTO;
import com.eam.LevelUpCorp.businessLayer.service.ModuleService;
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
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Modules", description = "Modules management")
@CrossOrigin(origins = "*")
public class ModuleController {

    private final ModuleService moduleService;

    // CREATE
    @Operation(summary = "Create module", description = "Create a new module in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Module created", content = @Content(schema = @Schema(implementation = ModuleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Data invalid", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleDTO moduleDTO) {
        log.info("Creating a new module: {}", moduleDTO);
        return ResponseEntity.status(201).body(moduleService.createModule(moduleDTO));
    }

    // READ ONE
    @Operation(summary = "Get a module by ID", description = "Get a specific module by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module found", content = @Content(schema = @Schema(implementation = ModuleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Module not found", content = @Content)
    })
    @Parameter(name = "id", description = "ID of module", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModule(@PathVariable Long id) {
        log.info("Fetching module with id: {}", id);
        return ResponseEntity.ok(moduleService.getModule(id));
    }

    // READ ALL
    @Operation(summary = "List modules", description = "Get all modules available")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modules found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ModuleDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<ModuleDTO>> getModules() {
        log.info("Fetching all modules");
        return ResponseEntity.ok(moduleService.getModules());
    }

    // UPDATE
    @Operation(summary = "Update module", description = "Update an existing module")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module updated", content = @Content(schema = @Schema(implementation = ModuleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Data invalid", content = @Content),
        @ApiResponse(responseCode = "404", description = "Module not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> updateModule(@PathVariable Long id, @RequestBody ModuleDTO moduleDTO) {
        log.info("Updating module with id: {}", id);
        return ResponseEntity.ok(moduleService.updateModule(id, moduleDTO));
    }

    // DELETE
    @Operation(summary = "Delete module", description = "Delete an existing module")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Module deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Module not found", content = @Content)
    })
    @Parameter(name = "id", description = "ID of module", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        log.info("Deleting module with id: {}", id);
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }

}
