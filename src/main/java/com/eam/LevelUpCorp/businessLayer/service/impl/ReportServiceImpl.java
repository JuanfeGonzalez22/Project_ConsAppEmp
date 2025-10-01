package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.service.ReportService;
import com.eam.LevelUpCorp.businessLayer.validate.ReportValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportDAO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl  implements ReportService {


    private final ReportDAO reportDAO;
    private final ReportValidate reportValidate;
    private final ReportMapper reportMapper;



    private GeneralReportDTO emptyGeneralReport() {
        return new GeneralReportDTO(
                0,          // totalUsers
                0,          // totalCourses
                0,          // totalRegistrations
                0,          // totalCertificates
                0.0,        // averageProgress
                0.0,        // averageScores
                new HashMap<>() // usersByRole
        );
    }
    /*
        Method for create report.
     */
    @Override
    public GeneralReportDTO createReport(ReportDTO reportDTO) {
        log.info("Crear un nuevo reporte {}", reportDTO);
        reportValidate.validateCreate(reportDTO);


        reportDAO.saveReport(reportDTO);
        GeneralReportDTO generalReportDTO = emptyGeneralReport();
        log.info("Reporte creado correctamente con ID: {}", generalReportDTO);
        return generalReportDTO;
    }

    @Override
    public GeneralReportDTO getReportById(Long id) {
        log.info("Retorna un reporte por ID: {}", id);
        reportValidate.validateSearch(id);

        ReportDTO reportDTO = reportDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte no encontrado con ID: {}", id);
                    return new RuntimeException("Reporte no encontrado con ID: " + id);
                });

        // Retornamos un GeneralReportDTO con valores en 0
        return emptyGeneralReport();
    }

    @Override
    public List<GeneralReportDTO> getAllReports() {
        log.info("Obtener todos los reportes");
        List<ReportDTO> reports = reportDAO.findAllReports();

        if (reports.isEmpty()) {
            log.warn("No se encontraron reportes");
            throw new RuntimeException("No hay reportes disponibles");
        }

        // Mapeamos cada reporte a un GeneralReportDTO con valores en 0
        List<GeneralReportDTO> generalReports = reports.stream()
                .map(r -> emptyGeneralReport())
                .toList();

        log.info("Encontrado {} reportes", generalReports.size());
        return generalReports;
    }

    @Override
    public void deleteReport(Long id) {
        log.info("Intentando eliminar reporte por ID: {}", id);
        getReportById(id); // Verifica que exista
        reportValidate.validateDelete(id);

        boolean deleted = reportDAO.deleteReportById(id);
        if (!deleted) {
            log.error("Error al eliminar el reporte con ID: {}", id);
            throw new RuntimeException("No se pudo eliminar el reporte con ID: " + id);
        }
        log.info("Reporte eliminado correctamente con ID: {}", id);
    }


    @Override
    public GeneralReportDTO updateReport(Long id, ReportDTO reportDTO) {
        log.info("Actualizando reporte con ID: {}", id);
        reportValidate.validateUpdate(id, reportDTO);

        reportDAO.updateReport(id, reportDTO)
                .orElseThrow(() -> {
                    log.warn("No se pudo actualizar el reporte con ID: {}", id);
                    return new RuntimeException("Reporte no encontrado con ID: " + id);
                });

        // Por ahora retornamos un GeneralReportDTO con valores en 0
        return emptyGeneralReport();
    }
}
