package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.FileResourceDTO;
import org.springframework.stereotype.Component;

@Component
public class FileResourceValidate {

    public void validateCreate(FileResourceDTO fileResourceDTO) {
        if (fileResourceDTO == null) {
            throw new IllegalArgumentException("El recurso de archivo no puede ser nulo");
        }
        if (fileResourceDTO.getFileName() == null || fileResourceDTO.getFileName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo es obligatorio");
        }
        if (fileResourceDTO.getFileType() == null || fileResourceDTO.getFileType().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de archivo es obligatorio");
        }
        if (fileResourceDTO.getModuleId() == null && fileResourceDTO.getEvaluationId() == null) {
            throw new IllegalArgumentException("El recurso debe estar asociado a un módulo o evaluación");
        }
        if (fileResourceDTO.getFileName().length() > 255) {
            throw new IllegalArgumentException("El nombre del archivo no puede exceder 255 caracteres");
        }
        if (fileResourceDTO.getFileType().length() > 100) {
            throw new IllegalArgumentException("El tipo de archivo no puede exceder 100 caracteres");
        }
    }

    public void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("FileResource ID es obligatorio y debe ser mayor a cero");
        }
    }

    public void validateModuleId(Long moduleId) {
        if (moduleId == null || moduleId <= 0) {
            throw new IllegalArgumentException("Module ID es obligatorio y debe ser mayor a cero");
        }
    }

    public void validateEvaluationId(Long evaluationId) {
        if (evaluationId == null || evaluationId <= 0) {
            throw new IllegalArgumentException("Evaluation ID es obligatorio y debe ser mayor a cero");
        }
    }

    public void validateUpdate(Long id, FileResourceDTO fileResourceDTO) {
        validateSearch(id);
        validateCreate(fileResourceDTO);
    }

    public void validateDelete(Long id) {
        validateSearch(id);
    }

    public void validateFileUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del archivo es obligatoria");
        }
        if (fileUrl.length() > 500) {
            throw new IllegalArgumentException("La URL del archivo no puede exceder 500 caracteres");
        }
        if (!fileUrl.startsWith("http://") && !fileUrl.startsWith("https://")) {
            throw new IllegalArgumentException("La URL del archivo debe ser válida (http:// o https://)");
        }
    }
}