package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {

    // Crear certificado
    CertificateDTO createCertificate(CertificateDTO certificateDTO);

    // Buscar por ID
    CertificateDTO getCertificate(Long id);

    // Listar todos
    List<CertificateDTO> getCertificates();

    // Actualizar
    CertificateDTO updateCertificate(Long id, CertificateDTO certificateDTO);

    // Eliminar
    void deleteCertificate(Long id);
}
