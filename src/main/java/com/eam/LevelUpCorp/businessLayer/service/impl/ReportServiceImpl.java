package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportStaticsDTO;
import com.eam.LevelUpCorp.businessLayer.service.ReportService;
import com.eam.LevelUpCorp.businessLayer.validate.ReportValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportDAO;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportsStaticsDAO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ReportMapper;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ReportsStaticsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl  implements ReportService {


    private final ReportDAO reportDAO;
    private final ReportValidate reportValidate;
    private final ReportMapper reportMapper;               // Mapper de ReportDTO y GeneralReportDTO

    private final ReportsStaticsDAO reportStaticsDAO;      // DAO para guardar reportes generados automáticamente
    private final ReportsStaticsMapper reportStaticsMapper; // Mapper para ReportStaticsDTO <-> Entity

    // Método auxiliar para generar un GeneralReportDTO vacío (totales en 0)
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
     * Crear un reporte manual (por admin) y generar automáticamente su reporte estadístico
     */
    @Override
    public GeneralReportDTO createReport(ReportDTO reportDTO) {
        log.info("Crear un nuevo reporte {}", reportDTO);
        reportValidate.validateCreate(reportDTO);

        // Guardamos el reporte manual primero
        reportDAO.saveReport(reportDTO);

        // Creamos un reporte estadístico automático con valores iniciales en 0
        ReportStaticsDTO staticsDTO = new ReportStaticsDTO(
                null,               // reportId, null para que la BD lo genere
                0,                  // totalUsers
                0,                  // totalCourses
                0,                  // totalRegistrations
                0,                  // totalCertificates
                0.0,                // averageProgress
                0.0,                // averageScores
                new HashMap<>(),    // usersByRole
                LocalDateTime.now(),// createdAt
                LocalDateTime.now() // updatedAt
        );

        // Guardamos el reporte estadístico
        reportStaticsDAO.save(staticsDTO);

        // Retornamos un GeneralReportDTO con valores iniciales
        return emptyGeneralReport();
    }




    @Override
    public GeneralReportDTO getReportById(Long id) {
        log.info("Retorna un reporte manual por ID: {}", id);
        reportValidate.validateSearch(id);

        ReportDTO reportDTO = reportDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte no encontrado con ID: {}", id);
                    return new RuntimeException("Reporte no encontrado con ID: " + id);
                });

        // Por ahora retornamos un GeneralReportDTO vacío
        return emptyGeneralReport();
    }

    @Override
    public List<GeneralReportDTO> getAllReports() {
        log.info("Obtener todos los reportes manuales");
        List<ReportDTO> reports = reportDAO.findAllReports();

        if (reports.isEmpty()) {
            log.warn("No se encontraron reportes manuales");
            throw new RuntimeException("No hay reportes disponibles");
        }

        // Por ahora, cada reporte manual se muestra como GeneralReportDTO vacío
        return reports.stream()
                .map(r -> emptyGeneralReport())
                .toList();
    }

    @Override
    public void deleteReport(Long id) {
        log.info("Intentando eliminar reporte manual por ID: {}", id);
        getReportById(id); // Verifica que exista
        reportValidate.validateDelete(id);

        boolean deleted = reportDAO.deleteReportById(id);
        if (!deleted) {
            log.error("Error al eliminar el reporte con ID: {}", id);
            throw new RuntimeException("No se pudo eliminar el reporte con ID: " + id);
        }

        // También podemos eliminar el reporte estadístico generado automáticamente
        reportStaticsDAO.deleteById(id);

        log.info("Reporte manual y su estadístico eliminado correctamente con ID: {}", id);
    }

    @Override
    public GeneralReportDTO updateReport(Long id, ReportDTO reportDTO) {
        log.info("Actualizando reporte manual con ID: {}", id);
        reportValidate.validateUpdate(id, reportDTO);

        reportDAO.updateReport(id, reportDTO)
                .orElseThrow(() -> {
                    log.warn("No se pudo actualizar el reporte con ID: {}", id);
                    return new RuntimeException("Reporte no encontrado con ID: " + id);
                });

        // Podríamos actualizar también el reporte estadístico si se desea
        // Por ahora retornamos GeneralReportDTO vacío
        return emptyGeneralReport();
    }
}
