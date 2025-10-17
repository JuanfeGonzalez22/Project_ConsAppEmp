package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CertificateResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.impl.CertificateServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.CertificateValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.CertificateDAO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CertificateEntity;
import com.eam.LevelUpCorp.persistenceLayer.repository.CertificateRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("CertificateService - Unit Tests")
public class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateDAO certificateDAO;

    @Spy
    private CertificateValidate certificateValidate;

    @InjectMocks
    private CertificateServiceImpl certificateService;

    private CertificateDTO validCertificateDTO;
    private CertificateResponseDTO validCertificateResponseDTO;
    private CertificateEntity validCertificateEntity;
    private Long validCertificateId;
    private Long validUserId;
    private Long validCourseId;
    private LocalDate validEmissionDate;

    @BeforeEach
    void setUp(){
        validCertificateId = 1L;
        validUserId = 5L;
        validCourseId = 10L;
        validEmissionDate = LocalDate.now();

        validCertificateDTO = new CertificateDTO(
                validUserId.intValue(),
                validCourseId.intValue(),
                validEmissionDate,
                "ABC123XYZ"
        );

        validCertificateResponseDTO = new CertificateResponseDTO(
                validCertificateId,
                validUserId.intValue(),
                validCourseId.intValue(),
                validEmissionDate,
                "ABC123XYZ"
        );

        validCertificateEntity = new CertificateEntity();
        validCertificateEntity.setId(validCertificateId);
        validCertificateEntity.setUserId(validUserId);
        validCertificateEntity.setCourseId(validCourseId);
        validCertificateEntity.setEmissionDate(validEmissionDate);
        validCertificateEntity.setHash("ABC123XYZ");
    }

    //Create
    @Test
    @DisplayName("CREATE - Certificado válido debe retornar DTO creado")
    void createCertificate_ValidData_ShouldReturnCreatedCertificate(){
        CertificateResponseDTO expectedCertificate = new CertificateResponseDTO(

                1L,
                validUserId.intValue(),
                validCourseId.intValue(),
                validEmissionDate,
                "HASH123XYZ"
        );

        when(certificateDAO.save(any(CertificateDTO.class))).thenReturn(expectedCertificate);

        CertificateResponseDTO result = certificateService.createCertificate(validCertificateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(validUserId.intValue());
        assertThat(result.getCourseId()).isEqualTo(validCourseId.intValue());
        assertThat(result.getHash()).isEqualTo("HASH123XYZ");

        verify(certificateDAO, times(1)).save(any(CertificateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Hash nulo debe lanzar IllegalArgumentException")
    void createCertificate_NullHash_ShouldThrowException(){
        validCertificateDTO.setHash(null);

        assertThatThrownBy(() -> certificateService.createCertificate(validCertificateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("hash es obligatorio");
        verify(certificateDAO, never()).save(any(CertificateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Hash vacío debe lanzar IllegalArgumentException")
    void createCertificate_EmptyHash_ShouldThrowException(){
        validCertificateDTO.setHash("");
        assertThatThrownBy(() -> certificateService.createCertificate(validCertificateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("hash es obligatorio");
    }

    @Test
    @DisplayName("CREATE - userId inválido debe lanzar IllegalArgumentException")
    void createCertificate_InvalidUserId_ShouldThrowException(){
        validCertificateDTO.setUserId(0);
        assertThatThrownBy(() -> certificateService.createCertificate(validCertificateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("userId invalido");
    }

    @Test
    @DisplayName("CREATE - courseId inválido debe lanzar IllegalArgumentException")
    void createCertificate_InvalidCourseId_ShouldThrowException(){
        validCertificateDTO.setCourseId(-1);
        assertThatThrownBy(() -> certificateService.createCertificate(validCertificateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("courseId invalido");
    }

    @Test
    @DisplayName("CREATE - EmissionDate nula debe lanzar IllegalArgumentException")
    void createCertificate_NullEmissionDate_ShouldThrowException(){
        validCertificateDTO.setIssueDate(null);
        assertThatThrownBy(() -> certificateService.createCertificate(validCertificateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("fecha de emision es obligatoria");
    }

    //Read
    @Test
    @DisplayName("READ - Certificado existente debe retornarse correctamente")
    void getCertificateById_Found_ShouldReturnCertificate() {

        CertificateResponseDTO certificate = new CertificateResponseDTO(1L, 5, 10, LocalDate.now(), "ABC123XYZ");

        when(certificateDAO.findById(1L)).thenReturn(Optional.of(certificate));

        CertificateResponseDTO result = certificateService.getCertificate(1L);

        assertNotNull(result);
        assertEquals(5, result.getUserId());
        assertEquals("ABC123XYZ", result.getHash());
        verify(certificateDAO, times(1)).findById(1L);
    }

    @Test
    @DisplayName("READ - Certificado inexistente debe lanzar excepción")
    void getCertificateById_NotFound_ShouldThrowException(){
        when(certificateDAO.findById(10L)).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> certificateService.getCertificate(10L));
        assertEquals("Certificado no encontrado con ID: 10", ex.getMessage());
    }

    @Test
    @DisplayName("READ - Debe retornar lista de certificados existentes")
    void getCertificates_ShouldReturnList(){
        CertificateResponseDTO c1 = new CertificateResponseDTO(1L, 5, 10, LocalDate.of(2025, 10, 1), "HASH001");

        CertificateResponseDTO c2 = new CertificateResponseDTO(1L, 6, 11, LocalDate.of(2025, 10, 2), "HASH002");

        when(certificateDAO.findAll()).thenReturn(List.of(c1,c2));

        List<CertificateResponseDTO> result = certificateService.getCertificates();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUserId()).isEqualTo(5);
        assertThat(result.get(1).getCourseId()).isEqualTo(11);
        verify(certificateDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ - Lista vacía debe lanzar excepción con mensaje apropiado")
    void getCertificates_Empty_ShouldThrowException() {
        when(certificateDAO.findAll()).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> certificateService.getCertificates());

        assertEquals("Certificado no disponible", ex.getMessage());
        verify(certificateDAO, times(1)).findAll();
    }

    //Update
    @Test
    @DisplayName("UPDATE - Certificado existente debe actualizarse correctamente")
    void updateCertificate_Existing_ShouldReturnUpdatedCertificate(){
        Long certificateId = 1L;

        CertificateResponseDTO existingCertificate = new CertificateResponseDTO(1L, 5, 10, LocalDate.of(2025, 10, 1), "HASH001");

        CertificateResponseDTO updatedCertificate = new CertificateResponseDTO(1L, 5, 10, LocalDate.of(2025, 10, 5), "HASH999XYZ");

        when(certificateDAO.findById(certificateId)).thenReturn(Optional.of(existingCertificate));
        when(certificateDAO.update(eq(certificateId), any(CertificateDTO.class))).thenReturn(Optional.of(updatedCertificate));


        CertificateResponseDTO result = certificateService.updateCertificate(certificateId, new CertificateDTO(5, 10, LocalDate.of(2025, 10, 5), "HASH999XYZ"));

        assertNotNull(result);
        assertEquals("HASH999XYZ", result.getHash());
        assertEquals(LocalDate.of(2025, 10, 5), result.getIssueDate());
        verify(certificateDAO, times(1)).update(eq(certificateId), any(CertificateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Certificado inexistente debe lanzar excepción")
    void updateCertificate_NonExistent_ShouldThrowException() {
        Long invalid = 99L;
        CertificateDTO updateCertificate = new CertificateDTO(5, 10, LocalDate.of(2025, 10, 5), "HASH_NEW");

        when(certificateDAO.findById(invalid)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> certificateService.updateCertificate(invalid, updateCertificate));

        assertEquals("Certificado no encontrado con ID: 99", ex.getMessage());
        verify(certificateDAO, never()).update(anyLong(), any(CertificateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Error en DAO al actualizar debe lanzar excepción")
    void updateCertificate_DAOError_ShouldThrowException() {
        Long certificateId = 1L;
        CertificateResponseDTO existingCertificate = new CertificateResponseDTO(1L, 5, 10, LocalDate.of(2025, 10, 1), "HASH001");

        CertificateDTO updatedCertificateDTO = new CertificateDTO(5, 10, LocalDate.of(2025, 10, 5), "HASH_FAIL");

        when(certificateDAO.findById(certificateId)).thenReturn(Optional.of(existingCertificate));
        when(certificateDAO.update(eq(certificateId), any(CertificateDTO.class))).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> certificateService.updateCertificate(certificateId, updatedCertificateDTO));

        assertEquals("Error actualizando certificado con ID: 1", ex.getMessage());
        verify(certificateDAO, times(1)).update(eq(certificateId), any(CertificateDTO.class));
    }

    //Delete
    @Test
    @DisplayName("DELETE - Certificado existente debe eliminarse correctamente")
    void deleteCertificate_Existing_ShouldDeleteSuccessfully() {
        Long certificateId = 1L;
        CertificateResponseDTO existingCertificate = new CertificateResponseDTO(1L, 5, 10, LocalDate.of(2025, 10, 1), "HASH001");

        when(certificateDAO.findById(certificateId)).thenReturn(Optional.of(existingCertificate));
        when(certificateDAO.deleteById(certificateId)).thenReturn(true);

        assertDoesNotThrow(() -> certificateService.deleteCertificate(certificateId));

        verify(certificateDAO, times(1)).findById(certificateId);
        verify(certificateDAO, times(1)).deleteById(certificateId);
    }

    @Test
    @DisplayName("DELETE - Certificado inexistente debe lanzar excepción")
    void deleteCertificate_NonExistent_ShouldThrowException() {
        Long invalidId = 99L;

        when(certificateDAO.findById(invalidId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> certificateService.deleteCertificate(invalidId));

        assertEquals("Certificado no encontrado con ID: 99", ex.getMessage());
        verify(certificateDAO, never()).deleteById(invalidId);
    }

    @Test
    @DisplayName("DELETE - Falla en eliminación debe lanzar excepción")
    void deleteCertificate_DAOFailure_ShouldThrowException() {
        Long certificateId = 1L;
        CertificateResponseDTO existingCertificate = new CertificateResponseDTO(1L, 5, 10, LocalDate.of(2025, 10, 1), "HASH001");

        when(certificateDAO.findById(certificateId)).thenReturn(Optional.of(existingCertificate));
        when(certificateDAO.deleteById(certificateId)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> certificateService.deleteCertificate(certificateId));

        assertEquals("Error eliminando certificado con ID: 1", ex.getMessage());
        verify(certificateDAO, times(1)).deleteById(certificateId);
    }
}