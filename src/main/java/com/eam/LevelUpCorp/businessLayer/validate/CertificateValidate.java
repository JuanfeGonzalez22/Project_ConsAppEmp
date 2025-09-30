package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import org.springframework.stereotype.Component;

@Component
public class CertificateValidate {

    public void validateCreate(CertificateDTO certificateDTO) {
        if (certificateDTO == null) {
            throw new IllegalArgumentException("Certificate is null");
        }
        if (certificateDTO.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (certificateDTO.getCourseId() <= 0) {
            throw new IllegalArgumentException("Invalid course ID");
        }
        if (certificateDTO.getIssueDate() == null) {
            throw new IllegalArgumentException("Issue date is required");
        }
        if (certificateDTO.getHash() == null || certificateDTO.getHash().trim().isEmpty()) {
            throw new IllegalArgumentException("Hash is required");
        }
    }

    public void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid certificate ID");
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
