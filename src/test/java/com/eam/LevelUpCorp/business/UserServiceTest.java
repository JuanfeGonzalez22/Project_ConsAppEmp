package com.eam.LevelUpCorp.business;

import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserRegisterDTO;
import com.eam.LevelUpCorp.businessLayer.service.impl.UserServiceImpl;
import com.eam.LevelUpCorp.businessLayer.validate.UserValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("UserService - Pruebas Unitarias")
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private UserValidate userValidate;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO validUser;
    private UserDTO invalidUser;
    private UserDTO updatedUser;

    @BeforeEach
    void setUp() {
        validUser = new UserDTO(1L, "Juan Pérez", "juan@example.com", "123456", "USER", "IT");
        invalidUser = new UserDTO(null, "", "", "123", "", "");
        updatedUser = new UserDTO(1L, "Juan Actualizado", "juan.actualizado@example.com", "654321", "ADMIN", "Tech");
    }

    @Test
    @DisplayName("CREATE - crear usuario valido correctamnete")
    void testCreateUserSucces(){
        doNothing().when(userValidate).validateCreate(any(UserDTO.class));
        when(userDAO.save(any(UserDTO.class))).thenReturn(validUser);

        UserDTO result = userService.createUser(validUser);

        assertNotNull(result);
        assertEquals(validUser.getFullName(), result.getFullName());
        verify(userValidate).validateCreate(any(UserDTO.class));
        verify(userDAO).save(any(UserDTO.class));
    }

    @Test
    @DisplayName("CREATE - Crear usuario inválido lanza excepción")
    void testCreateUserInvalidThrowsException(){
        doThrow(new RuntimeException("The user is null")).when(userValidate).validateCreate(any(UserDTO.class));

        assertThrows(RuntimeException.class, () -> userService.createUser(invalidUser));
    }

    //Read
    @Test
    @DisplayName("READ - Obtener usuario existente por ID")
    void testGetUserByIdFound(){
        when(userDAO.findById(anyLong())).thenReturn(Optional.of(validUser));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(validUser.getEmail(), result.getEmail());
        verify(userDAO).findById(anyLong());
    }

    @Test
    @DisplayName("READ - Obtener usuario no existente lanza excepción")
    void testGetUserByIdNotFound(){
        when(userDAO.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
    }

    @Test
    @DisplayName("READ - Obtener todos los usuario existentes")
    void testGetAllUsersSuccess(){
        when(userDAO.findAll()).thenReturn(List.of(validUser, updatedUser));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userDAO).findAll();
    }

    @Test
    @DisplayName("READ - No hay usuarios registrados lanza excepción")
    void testGetAllUsersEmptyThrowsException(){
        when(userDAO.findAll()).thenReturn(List.of());
        assertThrows(RuntimeException.class, () -> userService.getAllUsers());
    }

    // UPDATE
    @Test
    @DisplayName("UPDATE - Actualizar usuario existente correctamente")
    void testUpdateUserSuccess(){
        when(userDAO.findById(anyLong())).thenReturn(Optional.of(validUser));
        doNothing().when(userValidate).validateUpdate(anyLong(), any(UserDTO.class));
        when(userDAO.update(anyLong(), any(UserDTO.class))).thenReturn(Optional.of(updatedUser));

        UserDTO result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Juan Actualizado", result.getFullName());
        verify(userDAO).update(anyLong(), any(UserDTO.class));
    }

    @Test
    @DisplayName("READ - Intentar actualizar usuario inexistente lanza excepción")
    void  testUpdateUserNotFoundThrowsException(){
        lenient().when(userDAO.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(99L, updatedUser));
    }

    // DELETE
    @Test
    @DisplayName("DELETE - Eliminar usuario existente correctamente")
    void testDeleteUserSuccess(){
        when(userDAO.findById(anyLong())).thenReturn(Optional.of(validUser));
        when(userDAO.deleteById(anyLong())).thenReturn(true);

        userService.deleteUser(1L);
        verify(userDAO).deleteById(anyLong());
    }

    @Test
    @DisplayName("DELETE - Eliminar usuario inexistente lanza excepción")
    void testDeleteUserNotFoundThrowsException() {
        lenient().when(userDAO.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
    }

    // LOGIN
    @Test
    @DisplayName("LOGIN - Login exitoso con credenciales válidas")
    void testLoginSuccess() {
        when(userDAO.findByEmail(anyString())).thenReturn(Optional.of(validUser));

        UserDTO result = userService.login("juan@example.com", "123456");

        assertNotNull(result);
        assertEquals("juan@example.com", result.getEmail());
        verify(userDAO).findByEmail(anyString());
    }

    @Test
    @DisplayName("LOGIN - Login falla con contraseña incorrecta")
    void testLoginInvalidPasswordThrowsException() {
        when(userDAO.findByEmail(anyString())).thenReturn(Optional.of(validUser));

        assertThrows(RuntimeException.class, () -> userService.login("juan@example.com", "wrong"));
    }

    @Test
    @DisplayName("LOGIN - Login falla con email no existente")
    void testLoginEmailNotFoundThrowsException() {
        when(userDAO.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.login("noexist@example.com", "123456"));
    }

}
