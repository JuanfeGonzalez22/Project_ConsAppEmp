package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.ReportStaticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportStaticsRepository extends JpaRepository<ReportStaticsEntity, Long> {

    Optional<ReportStaticsEntity> findByReportId(Long reportId);
}
