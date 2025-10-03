package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.ReportStaticsDTO;

import java.util.List;
import java.util.Optional;

public interface ReportsStaticsService {


    // Crear un nuevo reporte generado
    ReportStaticsDTO createReport(ReportStaticsDTO dto);

    // Buscar por ID
    Optional<ReportStaticsDTO> getReportById(Long id);

    // Listar todos
    List<ReportStaticsDTO> getAllReports();

    // Actualizar un reporte existente
    Optional<ReportStaticsDTO> updateReport(Long id, ReportStaticsDTO dto);

    // Eliminar por ID
    boolean deleteReport(Long id);
}
