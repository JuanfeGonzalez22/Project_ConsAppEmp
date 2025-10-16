package com.eam.LevelUpCorp.business;
import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.impl.CourseServiceImpl;
import com.eam.LevelUpCorp.persistenceLayer.dao.CourseDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("CourseService - Pruebas Unitarias")
public class CourseServiceTest {

    @Mock
    private CourseDAO courseDAO;

    @InjectMocks
    private CourseServiceImpl courseService;

    private CourseDTO validCourseDTO;
    private CourseResponseDTO validCourseResponseDTO;

    @BeforeEach
    void setUp(){
        validCourseDTO = new CourseDTO(
                "Curso de Spring Boot",
                "Spring Boot Basico",
                LocalTime.of(2, 30, 0),
                1
        );

        validCourseResponseDTO = new CourseResponseDTO(
                1L, // ID
                "Spring Boot Basico",
                "Curso de Spring Boot",
                LocalTime.of(2, 30, 0),
                1
        );
    }

    //Create
    @Test
    @DisplayName("CREATE - Curso válido debe crearse correctamente")
    void createCourse_ValidData_ShouldReturnCreatedCourse() {
        when(courseDAO.save(any(CourseDTO.class))).thenReturn(validCourseResponseDTO);

        CourseResponseDTO result = courseService.createCourse(validCourseDTO);

        assertNotNull(result);
        assertEquals("Curso de Spring Boot", result.getTitle());
        verify(courseDAO, times(1)).save(any(CourseDTO.class));
    }

    @Test
    @DisplayName("CREATE - Título nulo debe lanzar IllegalArgumentException")
    void createCourse_NullTitle_ShouldThrowException() {
        validCourseDTO.setTitle(null);

        assertThatThrownBy(() -> courseService.createCourse(validCourseDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("titulo del curso es obligatorio");
        verify(courseDAO, never()).save(any(CourseDTO.class));
    }

    @Test
    @DisplayName("CREATE - Descripción vacía debe lanzar IllegalArgumentException")
    void createCourse_EmptyDescription_ShouldThrowException() {
        validCourseDTO.setDescription(" ");

        assertThatThrownBy(() -> courseService.createCourse(validCourseDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("descripcion del curso vacia");
    }

    @Test
    @DisplayName("CREATE - Nivel fuera de rango debe lanzar IllegalArgumentException")
    void createCourse_InvalidLevel_ShouldThrowException() {
        validCourseDTO.setLevel(5);

        assertThatThrownBy(() -> courseService.createCourse(validCourseDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nivel del curso debe estar entre 1 (básico) y 3 (avanzado)");
    }

    @Test
    @DisplayName("CREATE - Duración nula debe lanzar IllegalArgumentException")
    void createCourse_NullDuration_ShouldThrowException() {
        validCourseDTO.setEstimatedDuration(null);

        assertThatThrownBy(() -> courseService.createCourse(validCourseDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("duracion estimada es obligatorio");
    }

    //Read
    @Test
    @DisplayName("READ - Curso existente debe retornarse correctamente")
    void getCourse_Existing_ShouldReturnCourse() {
        when(courseDAO.findById(1L)).thenReturn(Optional.of(validCourseResponseDTO));

        CourseResponseDTO result = courseService.getCourse(1L);

        assertNotNull(result);
        assertEquals("Curso de Spring Boot", result.getTitle());
        verify(courseDAO, times(1)).findById(1L);
    }

    @Test
    @DisplayName("READ - Curso inexistente debe lanzar excepción")
    void getCourse_NotFound_ShouldThrowException() {
        when(courseDAO.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.getCourse(99L));

        assertEquals("Curso no encontrado con el ID: 99", ex.getMessage());
        verify(courseDAO, times(1)).findById(99L);
    }

    @Test
    @DisplayName("READ - Lista de cursos existente debe retornarse correctamente")
    void getCourses_ShouldReturnList() {
        when(courseDAO.findAll()).thenReturn(List.of(validCourseResponseDTO));

        List<CourseResponseDTO> result = courseService.getCourses();

        assertThat(result).hasSize(1);
        verify(courseDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ - Lista vacía debe lanzar excepción con mensaje apropiado")
    void getCourses_Empty_ShouldThrowException() {
        when(courseDAO.findAll()).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.getCourses());

        assertEquals("Cursos no disponibles", ex.getMessage());
        verify(courseDAO, times(1)).findAll();
    }

    //Update
    @Test
    @DisplayName("UPDATE - Curso existente debe actualizarse correctamente")
    void updateCourse_Existing_ShouldReturnUpdatedCourse() {
        CourseDTO updateCourseDTO = new CourseDTO( // ✅ Para el request
                "Curso de Spring Boot Actualizado",
                "Spring Boot Avanzado",
                LocalTime.of(3,0,0),
                2
        );

        CourseResponseDTO updatedResponseDTO = new CourseResponseDTO(
                1L,
                "Spring Boot Avanzado",
                "Curso de Spring Boot Actualizado",
                LocalTime.of(3,0,0),
                2
        );

        when(courseDAO.findById(1L)).thenReturn(Optional.of(validCourseResponseDTO));
        when(courseDAO.update(eq(1L), any(CourseDTO.class))).thenReturn(Optional.of(updatedResponseDTO));

        CourseResponseDTO result = courseService.updateCourse(1L, updateCourseDTO);

        assertNotNull(result);
        assertEquals("Spring Boot Avanzado", result.getDescription());
        assertEquals(2, result.getLevel());
        verify(courseDAO, times(1)).update(eq(1L), any(CourseDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Curso inexistente debe lanzar excepción")
    void updateCourse_NonExistent_ShouldThrowException() {
        CourseDTO updateCourseDTO = new CourseDTO(
                "Curso de Spring Boot",
                "Spring Boot Avanzado",
                LocalTime.of(3,0,0),
                2
        );

        when(courseDAO.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.updateCourse(99L, updateCourseDTO));

        assertEquals("Curso no encontrado con el ID: 99", ex.getMessage());
        verify(courseDAO, never()).update(anyLong(), any(CourseDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Falla en DAO debe lanzar excepción")
    void updateCourse_DAOError_ShouldThrowException() {
        when(courseDAO.findById(1L)).thenReturn(Optional.of(validCourseResponseDTO));
        when(courseDAO.update(eq(1L), any(CourseDTO.class))).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.updateCourse(1L, validCourseDTO));

        assertEquals("Error al actualizar curso con ID: 1", ex.getMessage());
        verify(courseDAO, times(1)).update(eq(1L), any(CourseDTO.class));
    }

    //Delete
    @Test
    @DisplayName("DELETE - Curso existente debe eliminarse correctamente")
    void deleteCourse_Existing_ShouldDeleteSuccessfully() {
        when(courseDAO.findById(1L)).thenReturn(Optional.of(validCourseResponseDTO));
        when(courseDAO.deleteById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> courseService.deleteCourse(1L));
        verify(courseDAO, times(1)).findById(1L);
        verify(courseDAO, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE - Intentar eliminar curso inexistente debe lanzar excepción")
    void deleteCourse_NonExistent_ShouldThrowException() {
        when(courseDAO.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.deleteCourse(99L));
        assertEquals("Curso no encontrado con el ID: 99", ex.getMessage());
        verify(courseDAO, times(1)).findById(99L);
        verify(courseDAO, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("DELETE - Falla en DAO al eliminar curso existente debe lanzar excepción")
    void deleteCourse_DAOFailure_ShouldThrowException() {
        when(courseDAO.findById(1L)).thenReturn(Optional.of(validCourseResponseDTO));
        when(courseDAO.deleteById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.deleteCourse(1L));
        assertEquals("Error al eliminar el curso con ID: 1", ex.getMessage());
        verify(courseDAO, times(1)).deleteById(1L);
    }
}