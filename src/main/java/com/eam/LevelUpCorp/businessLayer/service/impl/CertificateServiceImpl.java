package com.eam.LevelUpCorp.businessLayer.service.impl;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import com.eam.LevelUpCorp.businessLayer.service.CertificateService;
import com.eam.LevelUpCorp.businessLayer.validate.CertificateValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.CertificateDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final CertificateValidate certificateValidate;

    @Override
    public CertificateDTO createCertificate(CertificateDTO certificateDTO) {
        log.info("Creating new certificate: {}", certificateDTO);
        certificateValidate.validateCreate(certificateDTO);
        CertificateDTO created = certificateDAO.save(certificateDTO);
        log.info("Certificate created successfully: {}", created);
        return created;
    }

    @Override
    public CertificateDTO getCertificate(Long id) {
        log.info("Fetching certificate with ID: {}", id);
        certificateValidate.validateSearch(id);
        return certificateDAO.findById(id).orElseThrow(() -> {
            log.warn("Certificate not found with ID: {}", id);
            return new RuntimeException("Certificate not found with ID: " + id);
        });
    }

    @Override
    public List<CertificateDTO> getCertificates() {
        log.info("Fetching all certificates");
        List<CertificateDTO> certificates = certificateDAO.findAll();
        if (certificates.isEmpty()) {
            log.warn("No certificates found");
            throw new RuntimeException("No certificates available");
        }
        log.info("Found {} certificates", certificates.size());
        return certificates;
    }

    @Override
    public CertificateDTO updateCertificate(Long id, CertificateDTO certificateDTO) {
        log.info("Updating certificate with ID: {}", id);
        getCertificate(id); // ensure existence
        certificateValidate.validateUpdate(id, certificateDTO);

        CertificateDTO updated = certificateDAO.update(id, certificateDTO)
                .orElseThrow(() -> new RuntimeException("Error updating certificate with ID: " + id));
        log.info("Certificate updated successfully with ID: {}", id);
        return updated;
    }

    @Override
    public void deleteCertificate(Long id) {
        log.info("Deleting certificate with ID: {}", id);
        getCertificate(id); // ensure existence
        certificateValidate.validateDelete(id);

        boolean deleted = certificateDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error deleting certificate with ID: " + id);
        }
        log.info("Certificate deleted successfully with ID: {}", id);
    }
}
