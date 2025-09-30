package com.eam.LevelUpCorp.persistenceLayer.repository;

import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {


}
