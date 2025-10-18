package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.service.impl.ReportServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.ReportValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportDAO;
import com.eam.LevelUpCorp.persistenceLayer.dao.ReportsStaticsDAO;
import com.eam.LevelUpCorp.persistenceLayer.repository.CertificateRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.ReportRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReportService - Pruebas Unitarias")
public class ReportServiceTest {

    @Mock
    private ReportDAO reportDAO;

    @Mock
    private ReportsStaticsDAO reportStaticsDAO;

    @Mock
    private ReportValidate reportValidate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private ReportServiceImpl reportService;
    private ReportDTO validReportDTO;


    @BeforeEach
    void SetUp(){
        validReportDTO = new ReportDTO();
        validReportDTO.setUserId(1L);
        validReportDTO.setCourseId(10L);
        validReportDTO.setTitle("Reporte General");
        validReportDTO.setDescription("Descripcion del reporte");
    }

    //Create
    @Test
    @DisplayName("CREATE - Reporte válido crea correctamente")
    void createValidReport() {
        doNothing().when(reportValidate).validateCreate(validReportDTO);
        when(reportDAO.saveReport(validReportDTO)).thenReturn(validReportDTO);

        GeneralReportDTO created = reportService.createReport(validReportDTO);

        assertThat(created).isNotNull();
        assertThat(created.getTotalUsers()).isGreaterThanOrEqualTo(0);
        assertThat(created.getTotalCourses()).isGreaterThanOrEqualTo(0);
        assertThat(created.getTotalCertificates()).isGreaterThanOrEqualTo(0);
        verify(reportValidate).validateCreate(validReportDTO);
        verify(reportDAO).saveReport(validReportDTO);
    }

    @Test
    @DisplayName("CREATE - Reporte nulo o inválido lanza excepción")
    void createInvalidReportThrowsException() {
        ReportDTO invalidReport = null;
        doThrow(new IllegalArgumentException("Reporte inválido")).when(reportValidate).validateCreate(invalidReport);

        assertThatThrownBy(() -> reportService.createReport(invalidReport))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reporte inválido");
        verify(reportValidate).validateCreate(invalidReport);
        verifyNoInteractions(reportDAO);
    }

    //Read
    @Test
    @DisplayName("READ - Reporte existente retorna correctamente")
    void readExistingReport() {
        Long reportId = 1L;
        when(reportDAO.findById(reportId)).thenReturn(Optional.of(validReportDTO));

        GeneralReportDTO found = reportService.getReportById(reportId);

        assertThat(found).isNotNull();
        assertThat(found.getTotalUsers()).isGreaterThanOrEqualTo(0);
        assertThat(found.getTotalCourses()).isGreaterThanOrEqualTo(0);
        assertThat(found.getTotalCertificates()).isGreaterThanOrEqualTo(0);
        verify(reportDAO).findById(reportId);
    }

    @Test
    @DisplayName("READ - Reporte inexistente lanza excepción")
    void readNonExistingReportThrowsException() {
        Long reportId = 2L;
        when(reportDAO.findById(reportId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reportService.getReportById(reportId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Reporte no encontrado");
        verify(reportDAO).findById(reportId);
    }

    @Test
    @DisplayName("READ - Lista vacía lanza excepción")
    void readEmptyReportListThrowsException() {
        when(reportDAO.findAllReports()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> reportService.getAllReports())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No hay reportes disponibles");
        verify(reportDAO).findAllReports();
    }

    //Update
    @Test
    @DisplayName("UPDATE - Reporte existente se actualiza correctamente")
    void updateExistingReport() {
        Long reportId = 1L;
        ReportDTO updatedReport = new ReportDTO();
        updatedReport.setUserId(1L);
        updatedReport.setCourseId(10L);
        updatedReport.setTitle("Reporte Actualizado");
        updatedReport.setDescription("Descripcion actualizada");

        ReportDTO existingReport = new ReportDTO(1L, 10L, "Reporte General", "Descripcion original");

        when(reportDAO.findById(reportId)).thenReturn(Optional.of(existingReport));
        when(reportDAO.updateReport(reportId, updatedReport)).thenReturn(Optional.of(updatedReport));
        doNothing().when(reportValidate).validateUpdate(reportId, updatedReport);

        GeneralReportDTO result = reportService.updateReport(reportId, updatedReport);

        assertThat(result).isNotNull();
        assertThat(result.getTotalUsers()).isGreaterThanOrEqualTo(0);
        assertThat(result.getTotalCourses()).isGreaterThanOrEqualTo(0);
        assertThat(result.getTotalCertificates()).isGreaterThanOrEqualTo(0);

        verify(reportDAO).findById(reportId);
        verify(reportValidate).validateUpdate(reportId, updatedReport);
        verify(reportDAO).updateReport(reportId, updatedReport);
    }

    @Test
    @DisplayName("UPDATE - Reporte inexistente lanza excepción")
    void updateNonExistingReportThrowsException() {
        Long reportId = 2L;
        ReportDTO updatedReport = new ReportDTO();
        when(reportDAO.findById(reportId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reportService.updateReport(reportId, updatedReport))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Reporte no encontrado");
        verify(reportDAO).findById(reportId);
        verifyNoMoreInteractions(reportValidate);
        verify(reportDAO, never()).updateReport(anyLong(), any());
    }

    //Delete
    @Test
    @DisplayName("DELETE - Reporte existente se elimina correctamente")
    void deleteExistingReport() {
        Long reportId = 1L;

        when(reportDAO.findById(reportId)).thenReturn(Optional.of(validReportDTO));
        when(reportDAO.deleteReportById(reportId)).thenReturn(true);
        doNothing().when(reportValidate).validateDelete(reportId);
        when(reportStaticsDAO.deleteById(reportId)).thenReturn(true);

        assertThatCode(() -> reportService.deleteReport(reportId))
                .doesNotThrowAnyException();

        verify(reportDAO).findById(reportId);
        verify(reportValidate).validateDelete(reportId);
        verify(reportDAO).deleteReportById(reportId);
        verify(reportStaticsDAO).deleteById(reportId);
    }


    @Test
    @DisplayName("DELETE - Reporte inexistente lanza excepción")
    void deleteNonExistingReportThrowsException() {
        Long reportId = 2L;
        when(reportDAO.findById(reportId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reportService.deleteReport(reportId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Reporte no encontrado");
        verify(reportDAO).findById(reportId);
        verify(reportDAO, never()).deleteReportById(anyLong());
    }
}
