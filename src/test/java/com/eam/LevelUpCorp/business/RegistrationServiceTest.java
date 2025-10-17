package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.impl.RegistrationServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.RegistrationValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.RegistrationDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para RegistrationServiceImpl
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RegistrationService - Unit Tests")
public class RegistrationServiceTest {

    @Mock
    private RegistrationDAO registrationDAO;

    @Mock
    private RegistrationValidate registrationValidate;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    private RegistrationDTO validRegistrationDTO;
    private RegistrationResponseDTO validRegistrationResponseDTO;
    private Long existingRegistrationId;
    private Long nonExistingRegistrationId;


    @BeforeEach
    void setUp() {
        existingRegistrationId = 1L;
        nonExistingRegistrationId = 999L;

        validRegistrationDTO = new RegistrationDTO(
                101L,
                202L,
                "ACTIVO"
        );


        validRegistrationResponseDTO = new RegistrationResponseDTO(
                existingRegistrationId,
                101L,
                "Erick Bolivar",
                "eri32@gmail.com",
                202L,
                "Fundamentos de python",
                75.5,
                LocalDate.of(2025, 10, 15),
                "ACTIVO"
        );
    }


    @Test
    @DisplayName("CREATE - Cuando los datos son válidos, debe crear registro y retornar ResponseDTO")
    void createRegistration_WithValidData_ShouldReturnRegistrationResponseDTO() {

        doNothing().when(registrationValidate).validateCreate(validRegistrationDTO);
        when(registrationDAO.save(validRegistrationDTO)).thenReturn(validRegistrationResponseDTO);

        RegistrationResponseDTO result = registrationService.createRegistration(validRegistrationDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingRegistrationId);
        assertThat(result.getUserId()).isEqualTo(101L);
        assertThat(result.getCourseId()).isEqualTo(202L);
        assertThat(result.getStatus()).isEqualTo("ACTIVO");
        assertThat(result.getProgress()).isEqualTo(75.5);
        assertThat(result.getUserName()).isEqualTo("Erick Bolivar");

        verify(registrationValidate, times(1)).validateCreate(validRegistrationDTO);
        verify(registrationDAO, times(1)).save(validRegistrationDTO);
        verifyNoMoreInteractions(registrationValidate, registrationDAO);
    }

    @Test
    @DisplayName("CREATE - Cuando validación falla, debe propagar excepción")
    void createRegistration_WhenValidationFails_ShouldPropagateException() {

        doThrow(new IllegalArgumentException("Usuario no existe"))
                .when(registrationValidate).validateCreate(validRegistrationDTO);

        assertThatThrownBy(() -> registrationService.createRegistration(validRegistrationDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Usuario no existe");

        verify(registrationValidate, times(1)).validateCreate(validRegistrationDTO);
        verify(registrationDAO, never()).save(any(RegistrationDTO.class));
    }

    @Test
    @DisplayName("CREATE - Cuando DTO es nulo, debe lanzar excepción de validación")
    void createRegistration_WithNullDTO_ShouldThrowValidationException() {

        doThrow(new IllegalArgumentException("RegistrationDTO no puede ser nulo"))
                .when(registrationValidate).validateCreate(null);

        assertThatThrownBy(() -> registrationService.createRegistration(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no puede ser nulo");

        verify(registrationValidate, times(1)).validateCreate(null);
        verify(registrationDAO, never()).save(any());
    }


    @Test
    @DisplayName("GET BY ID - Cuando ID existe, debe retornar RegistrationResponseDTO")
    void getRegistration_WithExistingId_ShouldReturnRegistrationResponseDTO() {

        doNothing().when(registrationValidate).validateSearch(existingRegistrationId);
        when(registrationDAO.findById(existingRegistrationId))
                .thenReturn(Optional.of(validRegistrationResponseDTO));

        RegistrationResponseDTO result = registrationService.getRegistration(existingRegistrationId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingRegistrationId);
        assertThat(result.getUserName()).isEqualTo("Erick Bolivar");
        assertThat(result.getCourseTitle()).isEqualTo("Fundamentos de python");

        verify(registrationValidate, times(1)).validateSearch(existingRegistrationId);
        verify(registrationDAO, times(1)).findById(existingRegistrationId);
    }

    @Test
    @DisplayName("GET BY ID - Cuando ID no existe, debe lanzar IllegalArgumentException")
    void getRegistration_WithNonExistingId_ShouldThrowException() {

        doNothing().when(registrationValidate).validateSearch(nonExistingRegistrationId);
        when(registrationDAO.findById(nonExistingRegistrationId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.getRegistration(nonExistingRegistrationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Registration not found");

        verify(registrationValidate, times(1)).validateSearch(nonExistingRegistrationId);
        verify(registrationDAO, times(1)).findById(nonExistingRegistrationId);
    }

    @Test
    @DisplayName("GET BY ID - Cuando validación de búsqueda falla, debe propagar excepción")
    void getRegistration_WhenSearchValidationFails_ShouldPropagateException() {

        doThrow(new IllegalArgumentException("ID inválido"))
                .when(registrationValidate).validateSearch(existingRegistrationId);

        assertThatThrownBy(() -> registrationService.getRegistration(existingRegistrationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID inválido");

        verify(registrationDAO, never()).findById(anyLong());
    }


    @Test
    @DisplayName("GET ALL - Cuando hay registros, debe retornar lista de ResponseDTOs")
    void getAllRegistrations_WithExistingRegistrations_ShouldReturnList() {

        List<RegistrationResponseDTO> registrations = Arrays.asList(
                validRegistrationResponseDTO,
                new RegistrationResponseDTO(
                        2L, 102L, "María García", "maria@email.com",
                        203L, "Java Avanzado", 50.0, LocalDate.now(), "ACTIVO")
        );

        when(registrationDAO.findAll()).thenReturn(registrations);

        List<RegistrationResponseDTO> result = registrationService.getAllRegistrations();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting("userName")
                .containsExactly("Erick Bolivar", "María García");

        verify(registrationDAO, times(1)).findAll();
        verify(registrationValidate, never()).validateSearch(anyLong());
    }

    @Test
    @DisplayName("GET ALL - Cuando no hay registros, debe retornar lista vacía")
    void getAllRegistrations_WithNoRegistrations_ShouldReturnEmptyList() {

        when(registrationDAO.findAll()).thenReturn(Arrays.asList());

        List<RegistrationResponseDTO> result = registrationService.getAllRegistrations();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(registrationDAO, times(1)).findAll();
    }



    @Test
    @DisplayName("UPDATE - Cuando datos son válidos, debe actualizar y retornar RegistrationDTO")
    void updateRegistration_WithValidData_ShouldReturnUpdatedRegistrationDTO() {

        RegistrationDTO updatedDTO = new RegistrationDTO(
                101L,
                202L,
                "COMPLETADO");
        RegistrationDTO expectedUpdatedDTO = new RegistrationDTO(
                101L,
                202L,
                "COMPLETADO");

        doNothing().when(registrationValidate).validateUpdate(existingRegistrationId, updatedDTO);
        when(registrationDAO.update(existingRegistrationId, updatedDTO))
                .thenReturn(Optional.of(expectedUpdatedDTO));

        RegistrationDTO result = registrationService.updateRegistration(existingRegistrationId, updatedDTO);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("COMPLETADO");
        assertThat(result.getUserId()).isEqualTo(101L);

        verify(registrationValidate, times(1)).validateUpdate(existingRegistrationId, updatedDTO);
        verify(registrationDAO, times(1)).update(existingRegistrationId, updatedDTO);
    }

    @Test
    @DisplayName("UPDATE - Cuando ID no existe, debe lanzar IllegalArgumentException")
    void updateRegistration_WithNonExistingId_ShouldThrowException() {

        doNothing().when(registrationValidate).validateUpdate(nonExistingRegistrationId, validRegistrationDTO);
        when(registrationDAO.update(nonExistingRegistrationId, validRegistrationDTO))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.updateRegistration(nonExistingRegistrationId, validRegistrationDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Registration not found");

        verify(registrationValidate, times(1)).validateUpdate(nonExistingRegistrationId, validRegistrationDTO);
        verify(registrationDAO, times(1)).update(nonExistingRegistrationId, validRegistrationDTO);
    }

    @Test
    @DisplayName("UPDATE - Cuando validación de actualización falla, debe propagar excepción")
    void updateRegistration_WhenUpdateValidationFails_ShouldPropagateException() {
        RegistrationDTO invalidDTO = new RegistrationDTO(
                null,
                202L,
                "ACTIVO");

        doThrow(new IllegalArgumentException("Usuario ID es requerido"))
                .when(registrationValidate).validateUpdate(existingRegistrationId, invalidDTO);

        assertThatThrownBy(() -> registrationService.updateRegistration(existingRegistrationId, invalidDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Usuario ID es requerido");

        verify(registrationDAO, never()).update(anyLong(), any(RegistrationDTO.class));
    }


    @Test
    @DisplayName("DELETE - Cuando ID existe, debe eliminar registro sin errores")
    void deleteRegistration_WithExistingId_ShouldDeleteSuccessfully() {

        doNothing().when(registrationValidate).validateDelete(existingRegistrationId);
        when(registrationDAO.deleteById(existingRegistrationId)).thenReturn(true);

        assertThatCode(() -> registrationService.deleteRegistration(existingRegistrationId))
                .doesNotThrowAnyException();

        verify(registrationValidate, times(1)).validateDelete(existingRegistrationId);
        verify(registrationDAO, times(1)).deleteById(existingRegistrationId);
    }

    @Test
    @DisplayName("DELETE - Cuando validación de eliminación falla, debe propagar excepción")
    void deleteRegistration_WhenDeleteValidationFails_ShouldPropagateException() {

        doThrow(new IllegalArgumentException("No se puede eliminar registro completado"))
                .when(registrationValidate).validateDelete(existingRegistrationId);

        assertThatThrownBy(() -> registrationService.deleteRegistration(existingRegistrationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No se puede eliminar registro completado");

        verify(registrationDAO, never()).deleteById(anyLong());
    }


    @Test
    @DisplayName("VALIDATION - Cuando status es inválido, debe fallar validación")
    void createRegistration_WithInvalidStatus_ShouldFailValidation() {

        RegistrationDTO invalidStatusDTO = new RegistrationDTO(
                101L,
                202L,
                "INVALID_STATUS");

        doThrow(new IllegalArgumentException("Estado inválido: INVALID_STATUS"))
                .when(registrationValidate).validateCreate(invalidStatusDTO);

        assertThatThrownBy(() -> registrationService.createRegistration(invalidStatusDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Estado inválido");

        verify(registrationDAO, never()).save(any());
    }

    @Test
    @DisplayName("VALIDATION - Cuando userId es nulo, debe fallar validación")
    void createRegistration_WithNullUserId_ShouldFailValidation() {
        RegistrationDTO nullUserIdDTO = new RegistrationDTO(
                null,
                202L,
                "ACTIVO");

        doThrow(new IllegalArgumentException("User ID es requerido"))
                .when(registrationValidate).validateCreate(nullUserIdDTO);

        assertThatThrownBy(() -> registrationService.createRegistration(nullUserIdDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User ID es requerido");

        verify(registrationDAO, never()).save(any());
    }
}