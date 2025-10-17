package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.FileResourceDTO;
import com.eam.LevelUpCorp.businessLayer.service.FileResourceService;
import com.eam.LevelUpCorp.businessLayer.validate.FileResourceValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.FileResourceDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileResourceServiceImpl implements FileResourceService {

    private final FileResourceDAO fileResourceDAO;
    private final FileResourceValidate fileResourceValidate;

    @Override
    public FileResourceDTO createFileResource(FileResourceDTO fileResourceDTO) {
        fileResourceValidate.validateCreate(fileResourceDTO);
        log.info("Creando nuevo recurso de archivo: {}", fileResourceDTO.getFileName());
        FileResourceDTO createdFileResource = fileResourceDAO.save(fileResourceDTO);
        log.info("Recurso de archivo creado exitosamente con ID: {}", createdFileResource.getId());
        return createdFileResource;
    }

    @Override
    @Transactional(readOnly = true)
    public FileResourceDTO getFileResourceById(Long id) {
        fileResourceValidate.validateSearch(id);
        log.info("Obteniendo recurso de archivo por ID: {}", id);
        return fileResourceDAO.findById(id).orElseThrow(() -> {
            log.warn("Error al obtener recurso de archivo por ID: {}", id);
            return new RuntimeException("Recurso de archivo no encontrado con ID: " + id);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileResourceDTO> getAllFileResources() {
        log.info("Obteniendo todos los recursos de archivo");
        List<FileResourceDTO> fileResources = fileResourceDAO.findAll();
        if (fileResources.isEmpty()) {
            log.warn("No se encontraron recursos de archivo");
            throw new RuntimeException("No hay recursos de archivo disponibles");
        }
        log.info("Se encontraron {} recursos de archivo", fileResources.size());
        return fileResources;
    }

    @Override
    public void deleteFileResource(Long id) {
        fileResourceValidate.validateDelete(id);
        log.info("Eliminando recurso de archivo con ID: {}", id);
        getFileResourceById(id);
        boolean deleted = fileResourceDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error eliminando el recurso de archivo");
        }
        log.info("Recurso de archivo eliminado exitosamente ID: {}", id);
    }

    @Override
    public FileResourceDTO updateFileResource(Long id, FileResourceDTO fileResourceDTO) {
        fileResourceValidate.validateUpdate(id, fileResourceDTO);
        log.info("Actualizando recurso de archivo con ID: {}", id);
        getFileResourceById(id);
        FileResourceDTO updatedFileResource = fileResourceDAO.update(id, fileResourceDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar el recurso de archivo"));
        log.info("Recurso de archivo actualizado exitosamente ID: {}", id);
        return updatedFileResource;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileResourceDTO> getFileResourcesByModule(Long moduleId) {
        fileResourceValidate.validateModuleId(moduleId);
        log.info("Obteniendo recursos de archivo por módulo: {}", moduleId);
        List<FileResourceDTO> fileResources = fileResourceDAO.findByModuleId(moduleId);
        if (fileResources.isEmpty()) {
            log.warn("No se encontraron recursos de archivo para el módulo: {}", moduleId);
            throw new RuntimeException("No se encontraron recursos de archivo para el módulo: " + moduleId);
        }
        log.info("Se encontraron {} recursos de archivo para el módulo: {}", fileResources.size(), moduleId);
        return fileResources;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileResourceDTO> getFileResourcesByEvaluation(Long evaluationId) {
        fileResourceValidate.validateEvaluationId(evaluationId);
        log.info("Obteniendo recursos de archivo por evaluación: {}", evaluationId);
        List<FileResourceDTO> fileResources = fileResourceDAO.findByEvaluationId(evaluationId);
        if (fileResources.isEmpty()) {
            log.warn("No se encontraron recursos de archivo para la evaluación: {}", evaluationId);
            throw new RuntimeException("No se encontraron recursos de archivo para la evaluación: " + evaluationId);
        }
        log.info("Se encontraron {} recursos de archivo para la evaluación: {}", fileResources.size(), evaluationId);
        return fileResources;
    }
}