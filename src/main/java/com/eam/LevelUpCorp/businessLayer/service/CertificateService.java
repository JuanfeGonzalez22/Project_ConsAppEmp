package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {

    // Create a new certificate
    CertificateDTO createCertificate(CertificateDTO certificateDTO);

    // Find certificate by ID
    CertificateDTO getCertificate(Long id);

    // List all certificates
    List<CertificateDTO> getCertificates();

    // Update certificate
    CertificateDTO updateCertificate(Long id, CertificateDTO certificateDTO);

    // Delete certificate
    void deleteCertificate(Long id);
}
