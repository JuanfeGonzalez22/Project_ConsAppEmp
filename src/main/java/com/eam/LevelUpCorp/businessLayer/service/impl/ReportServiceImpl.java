package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.service.ReportService;
import com.eam.LevelUpCorp.businessLayer.validate.ReportValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportDAO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl  implements ReportService {


    private final ReportDAO reportDAO;
    private final ReportValidate reportValidate;


    /*
        Method for create report.
     */
    @Override
    public GeneralReportDTO createReport(ReportDTO reportDTO) {
        log.info("Crear un nuevo reporte {}", reportDTO);
        reportValidate.validateCreate(reportDTO);
        GeneralReportDTO  createReport = reportDAO.save(reportDTO);
        log.info("Reporte creado correctamente con ID: {}", createReport);
        
        return createReport;
    }


    /*
        Method for search a report.
     */
    @Override
    public GeneralReportDTO getReportById(Long id) {
        log.info("Retorna un nuevo reporte {}", id);
        reportValidate.validateSearch(id);
        return reportDAO.findById(id).orElseThrow(() -> {
            log.warn("Report not found with ID: {}", id);
            return new RuntimeException("Reporte no encontrado con ID: " + id);
        });
    }


    /*
       Method for get all reports.
     */
    @Override
    public List<GeneralReportDTO> getAllReports() {
        log.info("Obtener todos los reportes");
        List<GeneralReportDTO> reports = reportDAO.findAll();

        if (reports.isEmpty()) {
            log.warn("Reportes no encontrados");
            throw new RuntimeException("No reports available");
        }

        log.info("Encontrado {} reportes", reports.size());
        return reports;
    }


    /*
       Method for delete a report.
     */
    @Override
    public void deleteReport(Long id) {

        log.info("Reporte eliminado por ID: {}", id);

        getReportById(id);
        reportValidate.validateDelete(id);

        boolean deleted = reportDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar un reporte con ID: " + id);
        }

        log.info("Reporte eliminado existosamente con ID: {}", id);
    }


    /*
       Method for update a report.
     */
    @Override
    public GeneralReportDTO updateReport(Long id, ReportDTO reportDTO) {

        log.info("Actualizar reporte por ID: {}", id);

        getReportById(id);
        reportValidate.validateUpdate(id, reportDTO);

        GeneralReportDTO updatedReport = reportDAO.update(id, reportDTO)
                .orElseThrow(() -> new RuntimeException("Error actuaizando un reporte con ID: " + id));

        log.info("Reporte actualizado exitosamente con ID: {}", id);
        return updatedReport;

    }
}
