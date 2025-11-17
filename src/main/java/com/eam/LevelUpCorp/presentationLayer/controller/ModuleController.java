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
@Tag(name = "Modulos", description = "Gestión de módulos del sistema")
@CrossOrigin(origins = "http://localhost:4200")
public class ModuleController {

    private final ModuleService moduleService;

    // CREAR
    @Operation(summary = "Crear módulo", description = "Registra un nuevo módulo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Módulo creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ModuleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleDTO moduleDTO) {
        log.info("Creando un nuevo módulo: {}", moduleDTO);
        ModuleDTO created = moduleService.createModule(moduleDTO);
        return ResponseEntity.status(201).body(created);
    }

    // OBTENER UNO
    @Operation(summary = "Obtener módulo por ID", description = "Devuelve la información de un módulo específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Módulo encontrado",
                    content = @Content(schema = @Schema(implementation = ModuleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Módulo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModule(
            @Parameter(description = "ID del módulo a buscar", required = true)
            @PathVariable Long id) {
        log.info("Consultando módulo con ID: {}", id);
        ModuleDTO module = moduleService.getModule(id);
        return ResponseEntity.ok(module);
    }

    // OBTENER TODOS
    @Operation(summary = "Listar módulos", description = "Obtiene todos los módulos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de módulos obtenido correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ModuleDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<ModuleDTO>> getModules() {
        log.info("Listando todos los módulos disponibles");
        List<ModuleDTO> modules = moduleService.getModules();
        return ResponseEntity.ok(modules);
    }

    // ACTUALIZAR
    @Operation(summary = "Actualizar módulo", description = "Modifica los datos de un módulo existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Módulo actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ModuleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Módulo no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> updateModule(
            @Parameter(description = "ID del módulo a actualizar", required = true)
            @PathVariable Long id,
            @RequestBody ModuleDTO moduleDTO) {
        log.info("Actualizando módulo con ID: {}", id);
        ModuleDTO updated = moduleService.updateModule(id, moduleDTO);
        return ResponseEntity.ok(updated);
    }

    // ELIMINAR
    @Operation(summary = "Eliminar módulo", description = "Elimina un módulo del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Módulo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Módulo no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(
            @Parameter(description = "ID del módulo a eliminar", required = true)
            @PathVariable Long id) {
        log.info("Eliminando módulo con ID: {}", id);
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }

}