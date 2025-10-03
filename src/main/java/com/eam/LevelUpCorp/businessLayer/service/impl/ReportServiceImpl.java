package com.eam.LevelUpCorp.businessLayer.service.impl;


import com.eam.LevelUpCorp.businessLayer.dto.*;
import com.eam.LevelUpCorp.businessLayer.service.ReportService;
import com.eam.LevelUpCorp.businessLayer.validate.ReportValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportDAO;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportsStaticsDAO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ReportMapper;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ReportsStaticsMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.CertificateRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl  implements ReportService {


    private final ReportDAO reportDAO;
    private final ReportValidate reportValidate;

    private final ReportsStaticsDAO reportStaticsDAO;


    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CertificateRepository certificateRepository;






    private GeneralReportDTO buildGeneralReport() {
        long totalUsers = userRepository.count();
        long totalCourses = courseRepository.count();
        long totalCertificates = certificateRepository.count();

        Map<String, Long> usersByRole = userRepository.countUsersByRole().stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));

        return new GeneralReportDTO(
                (int) totalUsers,
                (int) totalCourses,
                (int) totalCertificates,
                0,
                0.0,
                0.0,
                usersByRole
        );
    }

    @Override
    public GeneralReportDTO createReport(ReportDTO reportDTO) {
        log.info("Crear un nuevo reporte {}", reportDTO);
        reportValidate.validateCreate(reportDTO);


        reportDAO.saveReport(reportDTO);


        return buildGeneralReport();
    }

    @Override
    public GeneralReportDTO getReportById(Long id) {
        log.info("Retorna un reporte manual por ID: {}", id);
        reportValidate.validateSearch(id);

        reportDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte no encontrado con ID: {}", id);
                    return new RuntimeException("Reporte no encontrado con ID: " + id);
                });

        return buildGeneralReport();
    }

    @Override
    public List<GeneralReportDTO> getAllReports() {
        log.info("Obtener todos los reportes manuales");
        List<ReportDTO> reports = reportDAO.findAllReports();

        if (reports.isEmpty()) {
            log.warn("No se encontraron reportes manuales");
            throw new RuntimeException("No hay reportes disponibles");
        }

        return reports.stream()
                .map(r -> buildGeneralReport())
                .toList();
    }

    @Override
    public void deleteReport(Long id) {
        log.info("Intentando eliminar reporte manual por ID: {}", id);
        getReportById(id);
        reportValidate.validateDelete(id);

        boolean deleted = reportDAO.deleteReportById(id);
        if (!deleted) {
            log.error("Error al eliminar el reporte con ID: {}", id);
            throw new RuntimeException("No se pudo eliminar el reporte con ID: " + id);
        }

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


        return buildGeneralReport();
    }

//    @Override
//    public InstructorReportDTO generateInstructorReport(Long instructorId) {
//        log.info("Generando reporte para instructor con ID: {}", instructorId);
//
//        UserEntity instructor = userRepository.findById(instructorId)
//                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
//
//        List<CourseEntity> courses = courseRepository.findByInstructorId(instructorId);
//
//        List<CourseSummaryDTO> courseSummaries = courses.stream().map(course -> {
//            long totalApprentices = 0;
//
//            double averageProgress = 0.0;
//            double averageScores = 0.0;
//
//            return new CourseSummaryDTO(
//                    course.getId(),
//                    course.getTitle(),
//                    totalApprentices,
//                    averageProgress,
//                    averageScores
//            );
//        }).toList();
//
//        return new InstructorReportDTO(
//                instructor.getId(),
//                instructor.getName(),
//                courseSummaries
//        );
//    }
}
