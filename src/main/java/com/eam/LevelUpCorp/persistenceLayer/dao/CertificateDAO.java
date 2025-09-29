package com.eam.LevelUpCorp.persistenceLayer.dao;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CertificateEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.CertificateMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CertificateDAO {

    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    // Save certificate
    public CertificateDTO save(CertificateDTO certificateDTO) {
        CertificateEntity entity = certificateMapper.toEntity(certificateDTO);

        // explicit int -> Long conversion
        entity.setUserId((long) certificateDTO.getUserId());
        entity.setCourseId((long) certificateDTO.getCourseId());

        CertificateEntity savedEntity = certificateRepository.save(entity);
        return certificateMapper.toDTO(savedEntity);
    }

    // Find certificate by ID
    public Optional<CertificateDTO> findById(Long id) {
        return certificateRepository.findById(id).map(certificateMapper::toDTO);
    }

    // Update certificate
    public Optional<CertificateDTO> update(Long id, CertificateDTO certificateDTO) {
        return certificateRepository.findById(id)
                .map(existingEntity -> {
                    certificateMapper.updateEntityFromDTO(certificateDTO, existingEntity);

                    // explicit int -> Long conversion
                    existingEntity.setUserId((long) certificateDTO.getUserId());
                    existingEntity.setCourseId((long) certificateDTO.getCourseId());

                    CertificateEntity updatedEntity = certificateRepository.save(existingEntity);
                    return certificateMapper.toDTO(updatedEntity);
                });
    }

    // Delete certificate
    public boolean deleteById(Long id) {
        if (certificateRepository.existsById(id)) {
            certificateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // List all certificates
    public List<CertificateDTO> findAll() {
        return certificateRepository.findAll()
                .stream()
                .map(certificateMapper::toDTO)
                .toList();
    }
}
