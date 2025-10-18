package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;
import com.eam.LevelUpCorp.businessLayer.service.EvaluationService;
import com.eam.LevelUpCorp.businessLayer.service.impl.EvaluationServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.EvaluationValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.EvaluationDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EvaluationService - Prueba unitaria")
public class EvaluationServiceTest {

    @Mock
    private EvaluationDAO evaluationDAO;

    @Spy
    private EvaluationValidate evaluationValidate;

    @InjectMocks
    private EvaluationServiceImpl evaluationService;
    private EvaluationDTO validEvaluationDTO;
    private Long validId;

    @BeforeEach
    void setUp(){
        validId = 1L;
        validEvaluationDTO = new EvaluationDTO(
                10L,
                "Examen final",
                "quiz",
                100
        );
    }

    //Create
    @Test
    @DisplayName("CREATE - Evaluacion valido debe crearse correctamente")
    void createEvaluation_ValidData_ShouldReturnCreatedEvaluation(){
        when(evaluationDAO.save(any(EvaluationDTO.class))).thenReturn(validEvaluationDTO);

        EvaluationDTO result = evaluationService.create(validEvaluationDTO);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Examen final");
        verify(evaluationDAO, times(1)).save(any(EvaluationDTO.class));

    }

    @Test
    @DisplayName("CREATE - Evaluación nula debe lanzar IllegalArgumentException")
    void createEvaluation_Null_ShouldThrowException() {
        assertThatThrownBy(() -> evaluationService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Evaluation cannot be null");
        verify(evaluationDAO, never()).save(any(EvaluationDTO.class));
    }

    @Test
    @DisplayName("CREATE - Título vacío debe lanzar IllegalArgumentException")
    void createEvaluation_EmptyTitle_ShouldThrowException() {
        validEvaluationDTO.setTitle("");
        assertThatThrownBy(() -> evaluationService.create(validEvaluationDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Evaluation title cannot be empty");
    }

    //Read
    @Test
    @DisplayName("READ - Evaluación existente debe retornarse correctamente")
    void getEvaluationById_Existing_ShouldReturnEvaluation() {
        when(evaluationDAO.findById(validId)).thenReturn(Optional.of(validEvaluationDTO));

        EvaluationDTO result = evaluationService.getById(validId);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Examen final");
        verify(evaluationDAO, times(1)).findById(validId);
    }

    @Test
    @DisplayName("READ - Evaluación inexistente debe lanzar excepción")
    void getEvaluationById_NonExisting_ShouldThrowException() {
        when(evaluationDAO.findById(validId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> evaluationService.getById(validId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Evaluation not found with ID");
    }

    @Test
    @DisplayName("READ - Debe retornar lista de evaluaciones existentes")
    void getAllEvaluations_ShouldReturnList() {
        when(evaluationDAO.findAll()).thenReturn(List.of(validEvaluationDTO));

        List<EvaluationDTO> result = evaluationService.findAll();

        assertThat(result).hasSize(1);
        verify(evaluationDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ - Lista vacía debe lanzar excepción")
    void getAllEvaluations_Empty_ShouldThrowException() {
        when(evaluationDAO.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> evaluationService.findAll())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No evaluations available");
    }

    @Test
    @DisplayName("READ - Evaluaciones por módulo deben retornarse correctamente")
    void getEvaluationByModuleId_ShouldReturnList() {
        when(evaluationDAO.findByModuleId(10L)).thenReturn(List.of(validEvaluationDTO));

        List<EvaluationDTO> result = evaluationService.getEvaluationByModuleId(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getModuleId()).isEqualTo(10L);
        verify(evaluationDAO, times(1)).findByModuleId(10L);
    }

    @Test
    @DisplayName("READ - Evaluaciones por módulo vacías deben lanzar excepción")
    void getEvaluationByModuleId_Empty_ShouldThrowException() {
        when(evaluationDAO.findByModuleId(10L)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> evaluationService.getEvaluationByModuleId(10L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No evaluations available for module ID");
    }

    //Update
    @Test
    @DisplayName("UPDATE - Evaluación existente debe actualizarse correctamente")
    void updateEvaluation_Existing_ShouldReturnUpdatedEvaluation() {
        when(evaluationDAO.update(eq(validId), any(EvaluationDTO.class)))
                .thenReturn(Optional.of(validEvaluationDTO));

        EvaluationDTO updated = evaluationService.update(validId, validEvaluationDTO);

        assertThat(updated).isNotNull();
        verify(evaluationDAO, times(1)).update(eq(validId), any(EvaluationDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Evaluación inexistente debe lanzar excepción")
    void updateEvaluation_NonExisting_ShouldThrowException() {
        when(evaluationDAO.update(eq(validId), any(EvaluationDTO.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> evaluationService.update(validId, validEvaluationDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error updating Evaluation");
    }

    //Delete
    @Test
    @DisplayName("DELETE - Evaluación existente debe eliminarse correctamente")
    void deleteEvaluation_Existing_ShouldDeleteSuccessfully() {
        when(evaluationDAO.deleteById(validId)).thenReturn(true);

        assertThatCode(() -> evaluationService.deleteById(validId))
                .doesNotThrowAnyException();

        verify(evaluationDAO, times(1)).deleteById(validId);
    }

    @Test
    @DisplayName("DELETE - Evaluación inexistente debe lanzar excepción")
    void deleteEvaluation_NonExisting_ShouldThrowException() {
        when(evaluationDAO.deleteById(validId)).thenReturn(false);

        assertThatThrownBy(() -> evaluationService.deleteById(validId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error deleting Evaluation");
    }
}
