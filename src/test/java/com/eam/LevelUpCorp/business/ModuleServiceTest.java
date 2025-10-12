package com.eam.LevelUpCorp.business;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import com.eam.LevelUpCorp.businessLayer.dto.ModuleDTO;
import com.eam.LevelUpCorp.businessLayer.service.impl.ModuleServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.ModuleValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ModuleDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
@DisplayName("ModuleService - Pruebas Unitarias")
public class ModuleServiceTest {

    @Mock
    private ModuleDAO moduleDAO;

    @Mock
    private ModuleValidate moduleValidate;

    @InjectMocks
    private ModuleServiceImpl moduleService;
    private ModuleDTO validModule;

    @BeforeEach
    void setUp(){
        validModule = new ModuleDTO();
        validModule.setCourseId(1001L);
        validModule.setTitle("Introducción a la plataforma");
        validModule.setType("video");
        validModule.setOrder(1);
    }

    // ======================================
    // CREATE TESTS
    // ======================================
    @Test
    @DisplayName("CREATE - crear módulo válido debe funcionar correctamente")
    void createModule_ValidData_ShouldReturnCreatedModule() {
        when(moduleDAO.save(any(ModuleDTO.class))).thenReturn(validModule);
        assertDoesNotThrow(() -> {
            ModuleDTO created = moduleService.createModule(validModule);
            assertEquals(validModule, created);
        });
        verify(moduleDAO).save(any(ModuleDTO.class));
    }

    @Test
    @DisplayName("CREATE - crear módulo con título nulo debe lanzar excepción")
    void createModule_NullTitle_ShouldThrowException() {
        validModule.setTitle(null);
        assertThatThrownBy(() -> moduleService.createModule(validModule))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("título del módulo es obligatorio");
    }

    @Test
    @DisplayName("CREATE - crear módulo con tipo vacío debe lanzar excepción")
    void createModule_EmptyType_ShouldThrowException() {
        validModule.setType("  ");
        assertThatThrownBy(() -> moduleService.createModule(validModule))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tipo del módulo es obligatorio");
    }

    @Test
    @DisplayName("CREATE - crear módulo con orden inválido debe lanzar excepción")
    void createModule_InvalidOrder_ShouldThrowException() {
        validModule.setOrder(0);
        assertThatThrownBy(() -> moduleService.createModule(validModule))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("orden del módulo es obligatorio y debe ser mayor a 0");
    }

    @Test
    @DisplayName("CREATE - crear módulo con courseId inválido debe lanzar excepción")
    void createModule_InvalidCourseId_ShouldThrowException() {
        validModule.setCourseId(0L);
        assertThatThrownBy(() -> moduleService.createModule(validModule))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cursoId es obligatorio y debe ser válido");
    }

    // ======================================
    // READ TESTS
    // ======================================
    @Test
    @DisplayName("READ - obtener módulo existente debe retornar módulo")
    void getModule_Existing_ShouldReturnModule() {
        when(moduleDAO.findById(1L)).thenReturn(java.util.Optional.of(validModule));
        ModuleDTO result = moduleService.getModule(1L);
        assertEquals(validModule, result);
        verify(moduleDAO).findById(1L);
    }

    @Test
    @DisplayName("READ - obtener módulo inexistente debe lanzar excepción")
    void getModule_NotFound_ShouldThrowException() {
        when(moduleDAO.findById(99L)).thenReturn(java.util.Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> moduleService.getModule(99L));
        assertThat(ex.getMessage()).contains("Módulo no encontrado con ID: 99");
    }

    @Test
    @DisplayName("READ - obtener todos los módulos debe retornar lista")
    void getModules_ShouldReturnList() {
        when(moduleDAO.findAll()).thenReturn(java.util.List.of(validModule));
        List<ModuleDTO> result = moduleService.getModules();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("READ - obtener todos los módulos vacío debe lanzar excepción")
    void getModules_Empty_ShouldThrowException() {
        when(moduleDAO.findAll()).thenReturn(java.util.List.of());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> moduleService.getModules());
        assertThat(ex.getMessage()).contains("No hay módulos disponibles");
    }

    // ======================================
    // UPDATE TESTS
    // ======================================
    @Test
    @DisplayName("UPDATE - actualizar módulo existente debe retornar módulo actualizado")
    void updateModule_Existing_ShouldReturnUpdatedModule() {
        ModuleDTO updatedModule = new ModuleDTO();
        updatedModule.setCourseId(validModule.getCourseId());
        updatedModule.setTitle("Nuevo título");
        updatedModule.setType("documento");
        updatedModule.setOrder(2);
        when(moduleDAO.findById(1L)).thenReturn(java.util.Optional.of(validModule));
        when(moduleDAO.update(org.mockito.ArgumentMatchers.eq(1L), any(ModuleDTO.class))).thenReturn(java.util.Optional.of(updatedModule));
        ModuleDTO result = moduleService.updateModule(1L, updatedModule);
        assertEquals(updatedModule.getTitle(), result.getTitle());
        assertEquals(updatedModule.getType(), result.getType());
        assertEquals(updatedModule.getOrder(), result.getOrder());
        assertEquals(updatedModule.getCourseId(), result.getCourseId());
    }

    @Test
    @DisplayName("UPDATE - actualizar módulo inexistente debe lanzar excepción")
    void updateModule_NonExistent_ShouldThrowException() {
        when(moduleDAO.findById(99L)).thenReturn(java.util.Optional.empty());
        ModuleDTO anyModule = new ModuleDTO();
        RuntimeException ex = assertThrows(RuntimeException.class, () -> moduleService.updateModule(99L, anyModule));
        assertThat(ex.getMessage()).contains("Módulo no encontrado con ID: 99");
    }

    @Test
    @DisplayName("UPDATE - error en DAO al actualizar debe lanzar excepción")
    void updateModule_DAOError_ShouldThrowException() {
        when(moduleDAO.findById(1L)).thenReturn(java.util.Optional.of(validModule));
        when(moduleDAO.update(org.mockito.ArgumentMatchers.eq(1L), any(ModuleDTO.class))).thenReturn(java.util.Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> moduleService.updateModule(1L, validModule));
        assertThat(ex.getMessage()).contains("Error al actualizar módulo con ID: 1");
    }

    // ======================================
    // DELETE TESTS
    // ======================================
    @Test
    @DisplayName("DELETE - eliminar módulo existente debe funcionar correctamente")
    void deleteModule_Existing_ShouldDeleteSuccessfully() {
        when(moduleDAO.findById(1L)).thenReturn(java.util.Optional.of(validModule));
        when(moduleDAO.deleteById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> moduleService.deleteModule(1L));
        verify(moduleDAO).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE - eliminar módulo inexistente debe lanzar excepción")
    void deleteModule_NonExistent_ShouldThrowException() {
        when(moduleDAO.findById(99L)).thenReturn(java.util.Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> moduleService.deleteModule(99L));
        assertThat(ex.getMessage()).contains("Módulo no encontrado con ID: 99");
    }

    @Test
    @DisplayName("DELETE - error en DAO al eliminar debe lanzar excepción")
    void deleteModule_DAOFailure_ShouldThrowException() {
        when(moduleDAO.findById(1L)).thenReturn(java.util.Optional.of(validModule));
        when(moduleDAO.deleteById(1L)).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> moduleService.deleteModule(1L));
        assertThat(ex.getMessage()).contains("Error al eliminar el módulo con ID: 1");
    }
}
