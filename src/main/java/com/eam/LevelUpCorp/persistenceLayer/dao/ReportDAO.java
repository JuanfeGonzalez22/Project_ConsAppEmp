package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
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


    //Save
    public GeneralReportDTO save(GeneralReportDTO generalReportDTO) {
        ReportEntity reportEntity = reportMapper.toEntity(generalReportDTO);
        ReportEntity savedReportEntity = reportRepository.save(reportEntity);
        return reportMapper.toDTO(savedReportEntity);

    }


    //Search
    public Optional<GeneralReportDTO> findById(Long id) {
        return reportRepository.findById(id).map(reportMapper::toDTO);

    }


    //Update
    public Optional<GeneralReportDTO> update(Long id, GeneralReportDTO generalReportDTO) {
        return reportRepository.findById(id)
                .map(existingEntity -> {
                    reportMapper.updateEntityFromDTO(generalReportDTO, existingEntity);
                    ReportEntity updatedEntity = reportRepository.save(existingEntity);
                    return reportMapper.toDTO(updatedEntity);
                });
    }


    //Delete
    public boolean deleteById(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //All Users.
    public List<GeneralReportDTO> findAll() {
        return reportRepository.findAll()
                .stream().map(reportMapper::toDTO).toList();
    }
}
