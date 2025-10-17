package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.CourseInstructorService;
import com.eam.LevelUpCorp.businessLayer.service.impl.CourseInstructorServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.CourseInstructorValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.CourseInstructorDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CourseInstructorService - Unit Tests")
public class CourseInstructorServiceTest {

    @Mock
    private CourseInstructorDAO courseInstructorDAO;

    @Mock
    private CourseInstructorValidate courseInstructorValidate;

    @InjectMocks
    private CourseInstructorServiceImpl courseInstructorService;

    private CourseInstructorDTO validCourseInstructorDTO;
    private CourseInstructorResponseDTO validCourseInstructorResponseDTO;
    private Long validAssignmentId;
    private Long validCourseId;
    private Long validInstructorId;

    @BeforeEach
    void setUp() {
        validAssignmentId = 1L;
        validCourseId = 5L;
        validInstructorId = 2L;

        validCourseInstructorDTO = new CourseInstructorDTO(
                validCourseId,
                validInstructorId
        );

        validCourseInstructorResponseDTO = new CourseInstructorResponseDTO(
                validAssignmentId,
                validCourseId,
                "Programación en Java",
                validInstructorId,
                "John Smith",
                LocalDate.now()
        );
    }


    @Test
    @DisplayName("CREATE - Asignación válida debe crearse correctamente")
    void createAssignment_ValidData_ShouldReturnCreatedAssignment() {
        doNothing().when(courseInstructorValidate).validateCreate(validCourseInstructorDTO);
        when(courseInstructorDAO.save(validCourseInstructorDTO)).thenReturn(validCourseInstructorResponseDTO);

        CourseInstructorResponseDTO result = courseInstructorService.createAssignment(validCourseInstructorDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validAssignmentId);
        assertThat(result.getCourseId()).isEqualTo(validCourseId);
        assertThat(result.getInstructorId()).isEqualTo(validInstructorId);
        assertThat(result.getCourseName()).isEqualTo("Programación en Java");
        assertThat(result.getInstructorName()).isEqualTo("John Smith");

        verify(courseInstructorValidate).validateCreate(validCourseInstructorDTO);
        verify(courseInstructorDAO).save(validCourseInstructorDTO);
    }

    @Test
    @DisplayName("CREATE - CourseId null debe lanzar excepción")
    void createAssignment_NullCourseId_ShouldThrowException() {
        CourseInstructorDTO invalidDTO = new CourseInstructorDTO(null, validInstructorId);

        doThrow(new IllegalArgumentException("Course ID es obligatorio"))
                .when(courseInstructorValidate).validateCreate(invalidDTO);

        assertThatThrownBy(() -> courseInstructorService.createAssignment(invalidDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Course ID es obligatorio");

        verify(courseInstructorDAO, never()).save(any(CourseInstructorDTO.class));
    }

    @Test
    @DisplayName("CREATE - InstructorId null debe lanzar excepción")
    void createAssignment_NullInstructorId_ShouldThrowException() {
        CourseInstructorDTO invalidDTO = new CourseInstructorDTO(validCourseId, null);

        doThrow(new IllegalArgumentException("Instructor ID es obligatorio"))
                .when(courseInstructorValidate).validateCreate(invalidDTO);

        assertThatThrownBy(() -> courseInstructorService.createAssignment(invalidDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Instructor ID es obligatorio");

        verify(courseInstructorDAO, never()).save(any(CourseInstructorDTO.class));
    }


    @Test
    @DisplayName("READ - Asignación existente debe retornarse correctamente")
    void getAssignmentById_ExistingId_ShouldReturnAssignment() {
        doNothing().when(courseInstructorValidate).validateSearch(validAssignmentId);
        when(courseInstructorDAO.findById(validAssignmentId)).thenReturn(Optional.of(validCourseInstructorResponseDTO));

        CourseInstructorResponseDTO result = courseInstructorService.getAssignmentById(validAssignmentId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validAssignmentId);
        assertThat(result.getCourseId()).isEqualTo(validCourseId);
        assertThat(result.getInstructorId()).isEqualTo(validInstructorId);

        verify(courseInstructorValidate).validateSearch(validAssignmentId);
        verify(courseInstructorDAO).findById(validAssignmentId);
    }

    @Test
    @DisplayName("READ - Asignación inexistente debe lanzar RuntimeException")
    void getAssignmentById_NonExistentId_ShouldThrowException() {
        Long nonExistentId = 999L;

        doNothing().when(courseInstructorValidate).validateSearch(nonExistentId);
        when(courseInstructorDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseInstructorService.getAssignmentById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Assignment not found with ID: " + nonExistentId);

        verify(courseInstructorValidate).validateSearch(nonExistentId);
        verify(courseInstructorDAO).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Con asignaciones existentes debe retornar lista")
    void getAllAssignments_WithAssignments_ShouldReturnList() {
        List<CourseInstructorResponseDTO> assignments = Arrays.asList(
                validCourseInstructorResponseDTO,
                new CourseInstructorResponseDTO(
                        2L,
                        6L,
                        "Spring Boot",
                        3L,
                        "Jane Doe", LocalDate.now())
        );

        when(courseInstructorDAO.findAll()).thenReturn(assignments);

        List<CourseInstructorResponseDTO> result = courseInstructorService.getAllAssignments();

        assertThat(result).hasSize(2);
        assertThat(result).extracting("courseName")
                .containsExactly("Programación en Java", "Spring Boot");

        verify(courseInstructorDAO).findAll();
    }

    @Test
    @DisplayName("READ ALL - Sin asignaciones debe lanzar RuntimeException")
    void getAllAssignments_EmptyList_ShouldThrowException() {
        when(courseInstructorDAO.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> courseInstructorService.getAllAssignments())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No course-instructor assignments available");

        verify(courseInstructorDAO).findAll();
    }


    @Test
    @DisplayName("UPDATE - Asignación existente debe actualizarse correctamente")
    void updateAssignment_ExistingAssignment_ShouldReturnUpdatedAssignment() {
        CourseInstructorDTO updateData = new CourseInstructorDTO(6L, 3L);
        CourseInstructorResponseDTO updatedResponse = new CourseInstructorResponseDTO(
                validAssignmentId,
                6L,
                "Nuevo Curso",
                3L,
                "Nuevo Instructor", LocalDate.now()
        );

        doNothing().when(courseInstructorValidate).validateUpdate(validAssignmentId, updateData);
        when(courseInstructorDAO.findById(validAssignmentId)).thenReturn(Optional.of(validCourseInstructorResponseDTO));
        when(courseInstructorDAO.update(eq(validAssignmentId), any(CourseInstructorDTO.class)))
                .thenReturn(Optional.of(updatedResponse));

        CourseInstructorResponseDTO result = courseInstructorService.updateAssignment(validAssignmentId, updateData);

        assertThat(result.getCourseId()).isEqualTo(6L);
        assertThat(result.getInstructorId()).isEqualTo(3L);
        assertThat(result.getCourseName()).isEqualTo("Nuevo Curso");
        assertThat(result.getInstructorName()).isEqualTo("Nuevo Instructor");

        verify(courseInstructorValidate).validateUpdate(validAssignmentId, updateData);
        verify(courseInstructorDAO).update(eq(validAssignmentId), any(CourseInstructorDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Asignación inexistente debe lanzar RuntimeException")
    void updateAssignment_NonExistentId_ShouldThrowException() {
        Long nonExistentId = 999L;
        CourseInstructorDTO updateData = new CourseInstructorDTO(validCourseId, validInstructorId);

        lenient().when(courseInstructorDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseInstructorService.updateAssignment(nonExistentId, updateData))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Assignment not found with ID: " + nonExistentId);

        verify(courseInstructorDAO, never()).update(anyLong(), any(CourseInstructorDTO.class));
    }



    @Test
    @DisplayName("UPDATE - Error en DAO debe lanzar RuntimeException")
    void updateAssignment_DAOFailure_ShouldThrowException() {
        CourseInstructorDTO updateData = new CourseInstructorDTO(
                6L,
                3L);

        doNothing().when(courseInstructorValidate).validateUpdate(validAssignmentId, updateData);
        when(courseInstructorDAO.findById(validAssignmentId)).thenReturn(Optional.of(validCourseInstructorResponseDTO));
        when(courseInstructorDAO.update(eq(validAssignmentId), any(CourseInstructorDTO.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseInstructorService.updateAssignment(validAssignmentId, updateData))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error updating assignment");

        verify(courseInstructorDAO).update(eq(validAssignmentId), any(CourseInstructorDTO.class));
    }


    @Test
    @DisplayName("DELETE - Asignación existente debe eliminarse correctamente")
    void deleteAssignment_ExistingId_ShouldDeleteSuccessfully() {
        doNothing().when(courseInstructorValidate).validateDelete(validAssignmentId);
        when(courseInstructorDAO.findById(validAssignmentId)).thenReturn(Optional.of(validCourseInstructorResponseDTO));
        when(courseInstructorDAO.deleteById(validAssignmentId)).thenReturn(true);

        assertThatCode(() -> courseInstructorService.deleteAssignment(validAssignmentId))
                .doesNotThrowAnyException();

        verify(courseInstructorValidate).validateDelete(validAssignmentId);
        verify(courseInstructorDAO).deleteById(validAssignmentId);
    }

    @Test
    @DisplayName("DELETE - Asignación inexistente debe lanzar RuntimeException")
    void deleteAssignment_NonExistentId_ShouldThrowException() {
        Long nonExistentId = 999L;

        lenient().when(courseInstructorDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseInstructorService.deleteAssignment(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Assignment not found with ID: " + nonExistentId);

        verify(courseInstructorDAO, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("DELETE - Error en DAO debe lanzar RuntimeException")
    void deleteAssignment_DAOFailure_ShouldThrowException() {
        doNothing().when(courseInstructorValidate).validateDelete(validAssignmentId);
        when(courseInstructorDAO.findById(validAssignmentId)).thenReturn(Optional.of(validCourseInstructorResponseDTO));
        when(courseInstructorDAO.deleteById(validAssignmentId)).thenReturn(false);

        assertThatThrownBy(() -> courseInstructorService.deleteAssignment(validAssignmentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error deleting assignment with ID: " + validAssignmentId);

        verify(courseInstructorDAO).deleteById(validAssignmentId);
    }


    @Test
    @DisplayName("EDGE CASE - IDs cero deben lanzar excepción")
    void createAssignment_ZeroIds_ShouldThrowException() {
        CourseInstructorDTO invalidDTO = new CourseInstructorDTO(
                0L,
                0L);

        doThrow(new IllegalArgumentException("IDs deben ser mayores a cero"))
                .when(courseInstructorValidate).validateCreate(invalidDTO);

        assertThatThrownBy(() -> courseInstructorService.createAssignment(invalidDTO))
                .isInstanceOf(IllegalArgumentException.class);

        verify(courseInstructorDAO, never()).save(any(CourseInstructorDTO.class));
    }

    @Test
    @DisplayName("EDGE CASE - IDs negativos deben lanzar excepción")
    void createAssignment_NegativeIds_ShouldThrowException() {
        CourseInstructorDTO invalidDTO = new CourseInstructorDTO(
                -1L,
                -2L);

        doThrow(new IllegalArgumentException("IDs deben ser mayores a cero"))
                .when(courseInstructorValidate).validateCreate(invalidDTO);

        assertThatThrownBy(() -> courseInstructorService.createAssignment(invalidDTO))
                .isInstanceOf(IllegalArgumentException.class);

        verify(courseInstructorDAO, never()).save(any(CourseInstructorDTO.class));
    }

    @Test
    @DisplayName("EDGE CASE - Misma asignación múltiples veces debe manejarse correctamente")
    void createAssignment_DuplicateAssignment_ShouldCreateSuccessfully() {
        CourseInstructorDTO duplicateDTO = new CourseInstructorDTO(validCourseId, validInstructorId);

        doNothing().when(courseInstructorValidate).validateCreate(duplicateDTO);
        when(courseInstructorDAO.save(duplicateDTO)).thenReturn(validCourseInstructorResponseDTO);

        CourseInstructorResponseDTO result = courseInstructorService.createAssignment(duplicateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getCourseId()).isEqualTo(validCourseId);
        assertThat(result.getInstructorId()).isEqualTo(validInstructorId);

        verify(courseInstructorValidate).validateCreate(duplicateDTO);
        verify(courseInstructorDAO).save(duplicateDTO);
    }
}