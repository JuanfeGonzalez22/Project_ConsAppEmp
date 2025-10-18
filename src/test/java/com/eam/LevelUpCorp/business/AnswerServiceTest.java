package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.AnswerResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.GradeAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.dto.SubmitAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.service.impl.AnswerServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.AnswerValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.AnswerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AnswerService - Prueba unitaria")
public class AnswerServiceTest {

    @Mock
    private AnswerDAO answerDAO;

    @Mock
    private AnswerValidate answerValidate;

    @InjectMocks
    private AnswerServiceImpl answerService;
    private AnswerResponseDTO validAnswer;
    private SubmitAnswerDTO validSubmit;
    private GradeAnswerDTO validGrade;
    private Long validId;

    @BeforeEach
    void setUp(){
        validId = 1L;
        validAnswer = new AnswerResponseDTO(
                2l,
                LocalDateTime.of(2024, 10, 17, 14, 30),
                12L,
                45L,
                85.0
        );

        validSubmit = new SubmitAnswerDTO();
        validSubmit.setEvaluationId(12L);

        validGrade = new GradeAnswerDTO();
        validGrade.setScore(90.0);
        validGrade.setFeedback("Buen trabajo");
    }

    //Create
    @Test
    @DisplayName("CREATE - Respuesta válida debe guardarse correctamente")
    void submitAnswer_Valid_ShouldReturnCreatedAnswer() {
        when(answerDAO.save(any(SubmitAnswerDTO.class), eq(45L), anyLong())).thenReturn(validAnswer);

        AnswerResponseDTO result = answerService.submitAnswer(validSubmit, 45L, 100L);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(45L);
        verify(answerValidate, times(1)).validateSubmitAnswer(validSubmit, 45L);
        verify(answerDAO, times(1)).save(validSubmit, 45L, 100L);
    }

    @Test
    @DisplayName("CREATE - Datos nulos deben lanzar IllegalArgumentException")
    void submitAnswer_NullData_ShouldThrowException() {
        assertThatThrownBy(() -> answerService.submitAnswer(null, 45L, 100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nulos");
        verify(answerDAO, never()).save(any(), anyLong(), anyLong());
    }

    //Read
    @Test
    @DisplayName("READ - Respuesta existente debe retornarse correctamente")
    void getAnswerById_Found_ShouldReturnAnswer() {
        when(answerDAO.findById(validId)).thenReturn(Optional.of(validAnswer));

        AnswerResponseDTO result = answerService.getAnswerById(validId);

        assertThat(result).isNotNull();
        assertThat(result.getScore()).isEqualTo(85.0);
        verify(answerValidate, times(1)).validateAnswerId(validId);
        verify(answerDAO, times(1)).findById(validId);
    }

    @Test
    @DisplayName("READ - Respuesta inexistente debe lanzar excepción")
    void getAnswerById_NotFound_ShouldThrowException() {
        when(answerDAO.findById(validId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> answerService.getAnswerById(validId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Respuesta no encontrada");
    }

    //Update
    @Test
    @DisplayName("UPDATE - Calificación válida debe actualizar la respuesta")
    void gradeAnswer_Valid_ShouldReturnUpdatedAnswer() {
        when(answerDAO.update(validId, validGrade)).thenReturn(Optional.of(validAnswer));

        AnswerResponseDTO result = answerService.gradeAnswer(validId, validGrade);

        assertThat(result).isNotNull();
        assertThat(result.getScore()).isEqualTo(85.0);
        verify(answerValidate, times(1)).validateGradeAnswer(validGrade);
        verify(answerDAO, times(1)).update(validId, validGrade);
    }

    @Test
    @DisplayName("UPDATE - ID inexistente debe lanzar excepción")
    void gradeAnswer_NotFound_ShouldThrowException() {
        when(answerDAO.update(validId, validGrade)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> answerService.gradeAnswer(validId, validGrade))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no encontrada");
    }

    //Delete
    @Test
    @DisplayName("DELETE - Respuesta existente debe eliminarse correctamente")
    void deleteAnswer_Existing_ShouldDeleteSuccessfully() {
        when(answerDAO.findById(validId)).thenReturn(Optional.of(validAnswer));
        when(answerDAO.deleteById(validId)).thenReturn(true);

        assertThatCode(() -> answerService.deleteAnswer(validId))
                .doesNotThrowAnyException();

        verify(answerDAO, times(1)).deleteById(validId);
    }

    @Test
    @DisplayName("DELETE - Respuesta inexistente debe lanzar excepción")
    void deleteAnswer_NonExistent_ShouldThrowException() {
        when(answerDAO.findById(validId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> answerService.deleteAnswer(validId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no encontrada");

        verify(answerDAO, never()).deleteById(validId);
    }
}
