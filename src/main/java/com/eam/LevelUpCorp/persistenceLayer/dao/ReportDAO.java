package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ReportMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportDAO {

    private final ReportMapper reportMapper;
    private final ReportRepository reportRepository;


    // Create
    public ReportDTO saveReport(ReportDTO reportDTO) {
        ReportEntity entity = reportMapper.toEntity(reportDTO);
        ReportEntity savedEntity = reportRepository.save(entity);
        return reportMapper.toDTO(savedEntity);
    }

    // Find by ID
    public Optional<ReportDTO> findById(Long id) {
        return reportRepository.findById(id)
                .map(reportMapper::toDTO);
    }

    // Update
    public Optional<ReportDTO> updateReport(Long id, ReportDTO reportDTO) {
        return reportRepository.findById(id).map(existingEntity -> {
            reportMapper.updateEntityFromDTO(reportDTO, existingEntity);
            ReportEntity updatedEntity = reportRepository.save(existingEntity);
            return reportMapper.toDTO(updatedEntity);
        });
    }

    // Delete
    public boolean deleteReportById(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Find all
    public List<ReportDTO> findAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(reportMapper::toDTO)
                .toList();
    }
}
