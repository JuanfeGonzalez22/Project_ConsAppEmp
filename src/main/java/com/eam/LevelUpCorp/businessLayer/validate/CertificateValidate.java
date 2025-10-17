package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import org.springframework.stereotype.Component;

@Component
public class CertificateValidate {

    public void validateCreate(CertificateDTO certificateDTO) {
        if (certificateDTO == null) {
            throw new IllegalArgumentException("El certificado es nulo");
        }
        if (certificateDTO.getUserId() <= 0) {
            throw new IllegalArgumentException("userId invalido");
        }
        if (certificateDTO.getCourseId() <= 0) {
            throw new IllegalArgumentException("courseId invalido");
        }
        if (certificateDTO.getIssueDate() == null) {
            throw new IllegalArgumentException("fecha de emision es obligatoria");
        }
        if (certificateDTO.getHash() == null || certificateDTO.getHash().trim().isEmpty()) {
            throw new IllegalArgumentException("hash es obligatorio");
        }
    }

    public void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de certificado invalido");
        }
    }

    public void validateUpdate(Long id, CertificateDTO certificateDTO) {
        validateSearch(id);
        validateCreate(certificateDTO);
    }

    public void validateDelete(Long id) {
        validateSearch(id);
    }
}
