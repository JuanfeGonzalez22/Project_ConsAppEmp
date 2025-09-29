package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {

    List<CertificateEntity> findByUserId(Long userId);

    List<CertificateEntity> findByCourseId(Long courseId);

    CertificateEntity findByHash(String hash);
}
