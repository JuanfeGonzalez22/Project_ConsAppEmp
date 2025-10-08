package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.FileResourceDTO;

import java.util.List;

public interface FileResourceService {


    // Create - Crear recurso de archivo
    FileResourceDTO createFileResource(FileResourceDTO fileResourceDTO);

    // Search by ID - Buscar por ID
    FileResourceDTO getFileResourceById(Long id);

    // Search for everyone - Todos los recursos
    List<FileResourceDTO> getAllFileResources();

    // Delete - Eliminar recurso
    void deleteFileResource(Long id);

    // Update - Actualizar recurso
    FileResourceDTO updateFileResource(Long id, FileResourceDTO fileResourceDTO);

    // Find by Module - Archivos por módulo
    List<FileResourceDTO> getFileResourcesByModule(Long moduleId);

    // Find by Evaluation - Archivos por evaluación
    List<FileResourceDTO> getFileResourcesByEvaluation(Long evaluationId);
}
