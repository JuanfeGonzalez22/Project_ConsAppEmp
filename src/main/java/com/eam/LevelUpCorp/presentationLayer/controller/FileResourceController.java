package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.FileResourceDTO;
import com.eam.LevelUpCorp.businessLayer.service.FileResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/file-resources")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Recursos de Archivo", description = "Gestión de archivos del sistema")
@CrossOrigin(origins = "http://localhost:4200")
public class FileResourceController {

    private final FileResourceService fileResourceService;

    // Directorio donde se guardarán los archivos
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    /**
     * Endpoint para subir archivos al servidor
     */
    @PostMapping("/upload")
    @Operation(summary = "Subir archivo", description = "Sube un archivo PDF al servidor y retorna la URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo subido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido o no permitido"),
            @ApiResponse(responseCode = "500", description = "Error al guardar el archivo")
    })
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Archivo a subir", required = true)
            @RequestParam("file") MultipartFile file
    ) {
        log.info("POST /api/v1/file-resources/upload - Subiendo archivo: {}", file.getOriginalFilename());

        // Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            log.warn("El archivo está vacío");
            return ResponseEntity.badRequest().body("El archivo está vacío");
        }

        // Validar tipo de archivo (solo PDF)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            log.warn("Tipo de archivo no permitido: {}", contentType);
            return ResponseEntity.badRequest().body("Solo se permiten archivos PDF");
        }

        // Validar tamaño (máximo 10MB)
        long maxSize = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxSize) {
            log.warn("Archivo demasiado grande: {} bytes", file.getSize());
            return ResponseEntity.badRequest().body("El archivo no debe superar los 10MB");
        }

        try {
            // Crear directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Directorio de uploads creado: {}", uploadPath);
            }

            // Generar nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".pdf";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Guardar el archivo
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Archivo guardado exitosamente: {}", filePath);

            // Construir URL del archivo (ajusta según tu configuración)
            String fileUrl = "http://localhost:8080/uploads/" + uniqueFilename;

            return ResponseEntity.ok(fileUrl);

        } catch (IOException e) {
            log.error("Error al guardar el archivo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el archivo: " + e.getMessage());
        }
    }

    /**
     * Crear un nuevo recurso de archivo
     */
    @PostMapping
    @Operation(summary = "Crear recurso de archivo", description = "Crea un nuevo recurso de archivo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso de archivo creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResourceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<FileResourceDTO> createFileResource(
            @Parameter(description = "Datos del recurso de archivo a crear", required = true)
            @RequestBody FileResourceDTO fileResourceDTO
    ) {
        log.info("POST /api/v1/file-resources - Creando recurso de archivo: {}", fileResourceDTO.getFileName());
        try {
            FileResourceDTO createdFileResource = fileResourceService.createFileResource(fileResourceDTO);
            log.info("Recurso de archivo creado con ID: {}", createdFileResource.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFileResource);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear recurso de archivo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtener recurso de archivo por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener recurso de archivo por ID", description = "Obtiene un recurso de archivo específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso de archivo encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResourceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Recurso de archivo no encontrado")
    })
    public ResponseEntity<FileResourceDTO> getFileResourceById(
            @Parameter(description = "ID del recurso de archivo", required = true)
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/file-resources/{} - Buscando recurso de archivo", id);
        try {
            FileResourceDTO fileResource = fileResourceService.getFileResourceById(id);
            return ResponseEntity.ok(fileResource);
        } catch (RuntimeException e) {
            log.warn("Recurso de archivo no encontrado con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener todos los recursos de archivo
     */
    @GetMapping
    @Operation(summary = "Listar recursos de archivo", description = "Obtiene todos los recursos de archivo del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recursos de archivo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResourceDTO.class)))
    })
    public ResponseEntity<List<FileResourceDTO>> getAllFileResources() {
        log.debug("GET /api/v1/file-resources - Obteniendo todos los recursos de archivo");
        List<FileResourceDTO> fileResources = fileResourceService.getAllFileResources();
        log.debug("Se encontraron {} recursos de archivo", fileResources.size());
        return ResponseEntity.ok(fileResources);
    }

    /**
     * Actualizar recurso de archivo
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar recurso de archivo", description = "Actualiza un recurso de archivo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso de archivo actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResourceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Recurso de archivo no encontrado")
    })
    public ResponseEntity<FileResourceDTO> updateFileResource(
            @Parameter(description = "ID del recurso de archivo a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos del recurso de archivo a actualizar", required = true)
            @RequestBody FileResourceDTO fileResourceDTO
    ) {
        log.info("PUT /api/v1/file-resources/{} - Actualizando recurso de archivo", id);
        try {
            FileResourceDTO updatedFileResource = fileResourceService.updateFileResource(id, fileResourceDTO);
            log.info("Recurso de archivo actualizado con ID: {}", id);
            return ResponseEntity.ok(updatedFileResource);
        } catch (RuntimeException e) {
            log.warn("Error al actualizar recurso de archivo ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar recurso de archivo
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar recurso de archivo", description = "Elimina un recurso de archivo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recurso de archivo eliminado"),
            @ApiResponse(responseCode = "404", description = "Recurso de archivo no encontrado")
    })
    public ResponseEntity<Void> deleteFileResource(
            @Parameter(description = "ID del recurso de archivo a eliminar", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/file-resources/{} - Eliminando recurso de archivo", id);
        try {
            fileResourceService.deleteFileResource(id);
            log.info("Recurso de archivo eliminado con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Recurso de archivo no encontrado para eliminar ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener recursos de archivo por módulo
     */
    @GetMapping("/module/{moduleId}")
    @Operation(summary = "Recursos de archivo por módulo", description = "Obtiene todos los recursos de archivo de un módulo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recursos de archivo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResourceDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron recursos de archivo")
    })
    public ResponseEntity<List<FileResourceDTO>> getFileResourcesByModule(
            @Parameter(description = "ID del módulo", required = true)
            @PathVariable Long moduleId
    ) {
        log.debug("GET /api/v1/file-resources/module/{} - Buscando recursos de archivo por módulo", moduleId);
        try {
            List<FileResourceDTO> fileResources = fileResourceService.getFileResourcesByModule(moduleId);
            log.debug("Se encontraron {} recursos de archivo para el módulo {}", fileResources.size(), moduleId);
            return ResponseEntity.ok(fileResources);
        } catch (RuntimeException e) {
            log.warn("No se encontraron recursos de archivo para el módulo: {}", moduleId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener recursos de archivo por evaluación
     */
    @GetMapping("/evaluation/{evaluationId}")
    @Operation(summary = "Recursos de archivo por evaluación", description = "Obtiene todos los recursos de archivo de una evaluación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recursos de archivo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResourceDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron recursos de archivo")
    })
    public ResponseEntity<List<FileResourceDTO>> getFileResourcesByEvaluation(
            @Parameter(description = "ID de la evaluación", required = true)
            @PathVariable Long evaluationId
    ) {
        log.debug("GET /api/v1/file-resources/evaluation/{} - Buscando recursos de archivo por evaluación", evaluationId);
        try {
            List<FileResourceDTO> fileResources = fileResourceService.getFileResourcesByEvaluation(evaluationId);
            log.debug("Se encontraron {} recursos de archivo para la evaluación {}", fileResources.size(), evaluationId);
            return ResponseEntity.ok(fileResources);
        } catch (RuntimeException e) {
            log.warn("No se encontraron recursos de archivo para la evaluación: {}", evaluationId);
            return ResponseEntity.notFound().build();
        }
    }
}