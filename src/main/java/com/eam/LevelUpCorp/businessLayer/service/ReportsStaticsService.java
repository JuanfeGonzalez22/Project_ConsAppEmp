package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.ReportStaticsDTO;

import java.util.List;
import java.util.Optional;

public interface ReportsStaticsService {


    // Create a report new
    ReportStaticsDTO createReport(ReportStaticsDTO dto);

    //Search by ID
    Optional<ReportStaticsDTO> getReportById(Long id);

    // Listar todos
    List<ReportStaticsDTO> getAllReports();

    // Update a report existing
    Optional<ReportStaticsDTO> updateReport(Long id, ReportStaticsDTO dto);

    // Delete
    boolean deleteReport(Long id);
}
