package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.ReportStaticsDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportStaticsEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ReportsStaticsMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.ReportStaticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportsStaticsDAO {

    private final ReportStaticsRepository reportStatsRepository;
    private final ReportsStaticsMapper reportStatsMapper;

    // Guardar un reporte generado
    public ReportStaticsDTO save(ReportStaticsDTO dto) {
        ReportStaticsEntity entity = reportStatsMapper.toEntity(dto);
        ReportStaticsEntity savedEntity = reportStatsRepository.save(entity);
        return reportStatsMapper.toDTO(savedEntity);
    }

    // Buscar por ID
    public Optional<ReportStaticsDTO> findById(Long id) {
        return reportStatsRepository.findById(id)
                .map(reportStatsMapper::toDTO);
    }

    // Actualizar
    public Optional<ReportStaticsDTO> update(Long id, ReportStaticsDTO dto) {
        return reportStatsRepository.findById(id)
                .map(existingEntity -> {
                    reportStatsMapper.updateEntityFromDTO(dto, existingEntity);
                    ReportStaticsEntity updatedEntity = reportStatsRepository.save(existingEntity);
                    return reportStatsMapper.toDTO(updatedEntity);
                });
    }

    // Eliminar
    public boolean deleteById(Long id) {
        if (reportStatsRepository.existsById(id)) {
            reportStatsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Listar todos
    public List<ReportStaticsDTO> findAll() {
        return reportStatsRepository.findAll()
                .stream()
                .map(reportStatsMapper::toDTO)
                .toList();
    }


}
