package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.FileResourceDTO;
import com.eam.LevelUpCorp.businessLayer.service.FileResourceService;
import com.eam.LevelUpCorp.businessLayer.service.impl.FileResourceServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.FileResourceValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.FileResourceDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileResourceService - Unit Tests")
public class FileResourceServiceTest {

    @Mock
    private FileResourceDAO fileResourceDAO;

    @Mock
    private FileResourceValidate fileResourceValidate;

    @InjectMocks
    private FileResourceServiceImpl fileResourceService;

    private FileResourceDTO validFileResourceDTO;
    private Long validFileResourceId;
    private Long validModuleId;
    private Long validEvaluationId;

    @BeforeEach
    void setUp() {
        validFileResourceId = 1L;
        validModuleId = 10L;
        validEvaluationId = 25L;

        validFileResourceDTO = new FileResourceDTO(
                null,
                "document.pdf",
                "application/pdf",
                validModuleId,
                null,
                "https://storage.com/files/document.pdf"
        );
    }


    @Test
    @DisplayName("CREATE - Recurso válido debe crearse correctamente")
    void createFileResource_ValidData_ShouldReturnCreatedResource() {
        FileResourceDTO expectedFileResource = new FileResourceDTO(
                validFileResourceId,
                "document.pdf",
                "application/pdf",
                validModuleId,
                null,
                "https://storage.com/files/document.pdf"
        );

        doNothing().when(fileResourceValidate).validateCreate(validFileResourceDTO);
        when(fileResourceDAO.save(any(FileResourceDTO.class))).thenReturn(expectedFileResource);

        FileResourceDTO result = fileResourceService.createFileResource(validFileResourceDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validFileResourceId);
        verify(fileResourceValidate).validateCreate(validFileResourceDTO);
        verify(fileResourceDAO).save(any(FileResourceDTO.class));
    }



    @Test
    @DisplayName("CREATE - Recurso con nombre null debe lanzar excepción")
    void createFileResource_NullFileName_ShouldThrowException() {
        validFileResourceDTO.setFileName(null);

        doThrow(new IllegalArgumentException("El nombre del archivo es obligatorio"))
                .when(fileResourceValidate).validateCreate(validFileResourceDTO);

        assertThatThrownBy(() -> fileResourceService.createFileResource(validFileResourceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del archivo es obligatorio");

        verify(fileResourceDAO, never()).save(any(FileResourceDTO.class));
    }

    @Test
    @DisplayName("CREATE - Recurso con tipo null debe lanzar excepción")
    void createFileResource_NullFileType_ShouldThrowException() {
        // ARRANGE - Crear un DTO específico para este test
        FileResourceDTO invalidFileResource = new FileResourceDTO(
                null,
                "document.pdf",
                null, // FileType null
                validModuleId,
                null,
                "https://storage.com/files/document.pdf"
        );

        // Configurar el mock para que lance excepción con el DTO específico
        doThrow(new IllegalArgumentException("El tipo de archivo es obligatorio"))
                .when(fileResourceValidate).validateCreate(invalidFileResource);

        // ACT & ASSERT
        assertThatThrownBy(() -> fileResourceService.createFileResource(invalidFileResource))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El tipo de archivo es obligatorio");

        // VERIFY - Asegurar que el DAO no fue llamado
        verify(fileResourceDAO, never()).save(any(FileResourceDTO.class));
        verify(fileResourceValidate, times(1)).validateCreate(invalidFileResource);
    }


    @Test
    @DisplayName("READ - Recurso existente debe retornarse correctamente")
    void getFileResourceById_ExistingId_ShouldReturnResource() {
        FileResourceDTO existingFileResource = new FileResourceDTO(
                validFileResourceId,
                "document.pdf",
                "application/pdf",
                validModuleId,
                null,
                "https://storage.com/files/document.pdf"
        );

        when(fileResourceDAO.findById(validFileResourceId))
                .thenReturn(Optional.of(existingFileResource));

        FileResourceDTO result = fileResourceService.getFileResourceById(validFileResourceId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validFileResourceId);
        assertThat(result.getFileName()).isEqualTo("document.pdf");

        verify(fileResourceDAO, times(1)).findById(validFileResourceId);
    }

    @Test
    @DisplayName("READ - Recurso inexistente debe lanzar RuntimeException")
    void getFileResourceById_NonExistentId_ShouldThrowException() {
        Long nonExistentId = 999L;
        when(fileResourceDAO.findById(nonExistentId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> fileResourceService.getFileResourceById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Recurso de archivo no encontrado con ID: " + nonExistentId);

        verify(fileResourceDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Con recursos existentes debe retornar lista")
    void getAllFileResources_WithResources_ShouldReturnList() {
        List<FileResourceDTO> fileResources = Arrays.asList(
                new FileResourceDTO(
                        1L,
                        "doc1.pdf",
                        "application/pdf",
                        1L,
                        null,
                        "url1"),
                new FileResourceDTO(
                        2L,
                        "doc2.pdf",
                        "application/pdf",
                        2L,
                        null,
                        "url2")
        );

        when(fileResourceDAO.findAll()).thenReturn(fileResources);

        List<FileResourceDTO> result = fileResourceService.getAllFileResources();

        assertThat(result).hasSize(2);
        assertThat(result).extracting("fileName")
                .containsExactly("doc1.pdf", "doc2.pdf");

        verify(fileResourceDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ ALL - Sin recursos debe lanzar RuntimeException")
    void getAllFileResources_EmptyList_ShouldThrowException() {
        when(fileResourceDAO.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> fileResourceService.getAllFileResources())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No hay recursos de archivo disponibles");

        verify(fileResourceDAO, times(1)).findAll();
    }


    @Test
    @DisplayName("UPDATE - Recurso existente debe actualizarse correctamente")
    void updateFileResource_ExistingResource_ShouldReturnUpdatedResource() {
        FileResourceDTO existingResource = new FileResourceDTO(
                validFileResourceId,
                "old.pdf",
                "application/pdf",
                1L,
                null,
                "old-url"
        );
        FileResourceDTO updateData = new FileResourceDTO(
                validFileResourceId,
                "new.pdf",
                "application/pdf",
                1L,
                null,
                "new-url"
        );
        FileResourceDTO updatedResource = new FileResourceDTO(
                validFileResourceId,
                "new.pdf",
                "application/pdf",
                1L,
                null,
                "new-url"
        );

        when(fileResourceDAO.findById(validFileResourceId))
                .thenReturn(Optional.of(existingResource));
        when(fileResourceDAO.update(eq(validFileResourceId), any(FileResourceDTO.class)))
                .thenReturn(Optional.of(updatedResource));

        FileResourceDTO result = fileResourceService.updateFileResource(validFileResourceId, updateData);

        assertThat(result.getFileName()).isEqualTo("new.pdf");
        assertThat(result.getFileUrl()).isEqualTo("new-url");

        verify(fileResourceDAO, times(1)).update(eq(validFileResourceId), any(FileResourceDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Recurso inexistente debe lanzar RuntimeException")
    void updateFileResource_NonExistentId_ShouldThrowException() {
        Long nonExistentId = 999L;
        FileResourceDTO updateData = new FileResourceDTO();

        when(fileResourceDAO.findById(nonExistentId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> fileResourceService.updateFileResource(nonExistentId, updateData))
                .isInstanceOf(RuntimeException.class);

        verify(fileResourceDAO, never()).update(anyLong(), any(FileResourceDTO.class));
    }



    @Test
    @DisplayName("DELETE - Recurso existente debe eliminarse correctamente")
    void deleteFileResource_ExistingId_ShouldDeleteSuccessfully() {
        FileResourceDTO existingResource = new FileResourceDTO(
                validFileResourceId,
                "document.pdf",
                "application/pdf",
                1L,
                null,
                "url"

        );

        when(fileResourceDAO.findById(validFileResourceId))
                .thenReturn(Optional.of(existingResource));
        when(fileResourceDAO.deleteById(validFileResourceId)).thenReturn(true);

        assertThatCode(() -> fileResourceService.deleteFileResource(validFileResourceId))
                .doesNotThrowAnyException();

        verify(fileResourceDAO, times(1)).deleteById(validFileResourceId);
    }

    @Test
    @DisplayName("DELETE - Recurso inexistente debe lanzar RuntimeException")
    void deleteFileResource_NonExistentId_ShouldThrowException() {
        Long nonExistentId = 999L;

        when(fileResourceDAO.findById(nonExistentId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> fileResourceService.deleteFileResource(nonExistentId))
                .isInstanceOf(RuntimeException.class);

        verify(fileResourceDAO, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("DELETE - Error en DAO debe lanzar RuntimeException")
    void deleteFileResource_DAOFailure_ShouldThrowException() {
        FileResourceDTO existingResource = new FileResourceDTO(
                validFileResourceId,
                "document.pdf",
                "application/pdf",
                1L,
                null,
                "url"
        );

        when(fileResourceDAO.findById(validFileResourceId))
                .thenReturn(Optional.of(existingResource));
        when(fileResourceDAO.deleteById(validFileResourceId)).thenReturn(false);

        assertThatThrownBy(() -> fileResourceService.deleteFileResource(validFileResourceId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error eliminando el recurso de archivo");

        verify(fileResourceDAO, times(1)).deleteById(validFileResourceId);
    }

    // ==================== FIND BY MODULE TESTS ====================

    @Test
    @DisplayName("FIND BY MODULE - Módulo con recursos debe retornar lista")
    void getFileResourcesByModule_ModuleWithResources_ShouldReturnList() {
        List<FileResourceDTO> moduleResources = Arrays.asList(
                new FileResourceDTO(
                        1L,
                        "doc1.pdf",
                        "application/pdf",
                        validModuleId,
                        null,
                        "url1"),
                new FileResourceDTO(
                        2L,
                        "doc2.pdf",
                        "application/pdf",
                        validModuleId,
                        null,
                        "url2")
        );

        when(fileResourceDAO.findByModuleId(validModuleId))
                .thenReturn(moduleResources);

        List<FileResourceDTO> result = fileResourceService.getFileResourcesByModule(validModuleId);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(resource -> resource.getModuleId().equals(validModuleId));

        verify(fileResourceDAO, times(1)).findByModuleId(validModuleId);
    }

    @Test
    @DisplayName("FIND BY MODULE - Módulo sin recursos debe lanzar RuntimeException")
    void getFileResourcesByModule_ModuleWithoutResources_ShouldThrowException() {
        when(fileResourceDAO.findByModuleId(validModuleId))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> fileResourceService.getFileResourcesByModule(validModuleId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se encontraron recursos de archivo para el módulo: " + validModuleId);

        verify(fileResourceDAO, times(1)).findByModuleId(validModuleId);
    }

    // ==================== FIND BY EVALUATION TESTS ====================

    @Test
    @DisplayName("FIND BY EVALUATION - Evaluación con recursos debe retornar lista")
    void getFileResourcesByEvaluation_EvaluationWithResources_ShouldReturnList() {
        List<FileResourceDTO> evaluationResources = Arrays.asList(
                new FileResourceDTO(
                        1L,
                        "exam1.pdf",
                        "application/pdf",
                        null,
                        validEvaluationId,
                        "url1"),
                new FileResourceDTO(
                        2L,
                        "exam2.pdf",
                        "application/pdf",
                        null, validEvaluationId,
                        "url2")
        );

        when(fileResourceDAO.findByEvaluationId(validEvaluationId))
                .thenReturn(evaluationResources);

        List<FileResourceDTO> result = fileResourceService.getFileResourcesByEvaluation(validEvaluationId);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(resource -> resource.getEvaluationId().equals(validEvaluationId));

        verify(fileResourceDAO, times(1)).findByEvaluationId(validEvaluationId);
    }

    @Test
    @DisplayName("FIND BY EVALUATION - Evaluación sin recursos debe lanzar RuntimeException")
    void getFileResourcesByEvaluation_EvaluationWithoutResources_ShouldThrowException() {
        when(fileResourceDAO.findByEvaluationId(validEvaluationId))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> fileResourceService.getFileResourcesByEvaluation(validEvaluationId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se encontraron recursos de archivo para la evaluación: " + validEvaluationId);

        verify(fileResourceDAO, times(1)).findByEvaluationId(validEvaluationId);
    }


    @Test
    @DisplayName("EDGE CASE - Recurso con ambos IDs (módulo y evaluación) debe manejarse correctamente")
    void createFileResource_WithBothModuleAndEvaluation_ShouldCreateSuccessfully() {
        FileResourceDTO resourceWithBoth = new FileResourceDTO(
                null,
                "shared.pdf",
                "application/pdf",
                validModuleId,
                validEvaluationId,
                "https://storage.com/files/shared.pdf"
        );

        FileResourceDTO expectedResource = new FileResourceDTO(
                validFileResourceId,
                "shared.pdf",
                "application/pdf",
                validModuleId,
                validEvaluationId,
                "https://storage.com/files/shared.pdf"
        );

        when(fileResourceDAO.save(any(FileResourceDTO.class)))
                .thenReturn(expectedResource);

        FileResourceDTO result = fileResourceService.createFileResource(resourceWithBoth);

        assertThat(result).isNotNull();
        assertThat(result.getModuleId()).isEqualTo(validModuleId);
        assertThat(result.getEvaluationId()).isEqualTo(validEvaluationId);

        verify(fileResourceDAO, times(1)).save(any(FileResourceDTO.class));
    }

    @Test
    @DisplayName("EDGE CASE - Recurso sin módulo ni evaluación debe manejarse correctamente")
    void createFileResource_WithoutModuleOrEvaluation_ShouldCreateSuccessfully() {
        FileResourceDTO resourceWithoutRelations = new FileResourceDTO(
                null,
                "general.pdf",
                "application/pdf",
                null,
                null,
                "https://storage.com/files/general.pdf"
        );

        FileResourceDTO expectedResource = new FileResourceDTO(
                validFileResourceId,
                "general.pdf",
                "application/pdf",
                null,
                null,
                "https://storage.com/files/general.pdf"
        );

        when(fileResourceDAO.save(any(FileResourceDTO.class)))
                .thenReturn(expectedResource);

        FileResourceDTO result = fileResourceService.createFileResource(resourceWithoutRelations);

        assertThat(result).isNotNull();
        assertThat(result.getModuleId()).isNull();
        assertThat(result.getEvaluationId()).isNull();

        verify(fileResourceDAO, times(1)).save(any(FileResourceDTO.class));
    }
}