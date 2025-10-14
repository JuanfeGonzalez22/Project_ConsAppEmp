package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CertificateResponseDTO;

import java.util.List;

public interface CertificateService {

    // Create a new certificate
    CertificateResponseDTO createCertificate(CertificateDTO certificateDTO);

    // Find certificate by ID
    CertificateResponseDTO getCertificate(Long id);

    // List all certificates
    List<CertificateResponseDTO> getCertificates();

    // Update certificate
    CertificateResponseDTO updateCertificate(Long id, CertificateDTO certificateDTO);

    // Delete certificate
    void deleteCertificate(Long id);
}
