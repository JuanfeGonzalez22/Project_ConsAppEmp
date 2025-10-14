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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
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
        MockitoAnnotations.openMocks(this);
        validUser = new UserDTO(1L, "Juan Pérez", "juan@example.com", "123456", "USER", "IT");
        invalidUser = new UserDTO(null, "", "", "123", "", "");
        updatedUser = new UserDTO(1L, "Juan Actualizado", "juan.actualizado@example.com", "654321", "ADMIN", "Tech");
    }

    @Test
    @DisplayName("CREATE - crear usuario valido correctamnete")
    void testCreateUserSucces(){
        doNothing().when(userValidate).validateCreate(validUser);
        when(userDAO.save(validUser)).thenReturn(validUser);

        UserDTO result = userService.createUser(validUser);

        assertNotNull(result);
        assertEquals(validUser.getFullName(), result.getFullName());
        verify(userValidate).validateCreate(validUser);
        verify(userDAO).save(validUser);
    }

    @Test
    @DisplayName("CREATE - Crear usuario inválido lanza excepción")
    void testCreateUserInvalidThrowsException(){
        doThrow(new IllegalArgumentException("The user is null")).when(userValidate).validateCreate(invalidUser);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(invalidUser));
        verify(userValidate).validateCreate(invalidUser);
        verify(userDAO, never()).save(any());
    }

    //Read
    @Test
    @DisplayName("READ - Obtener usuario existente por ID")
    void testGetUserByIdFound(){
        when(userDAO.findById(1L)).thenReturn(Optional.of(validUser));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(validUser.getEmail(), result.getEmail());
        verify(userDAO).findById(1L);
    }

    @Test
    @DisplayName("READ - Obtener usuario no existente lanza excepción")
    void testGetUserByIdNotFound(){
        when(userDAO.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
        verify(userDAO).findById(99L);
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
        verify(userDAO).findAll();
    }

    // UPDATE
    @Test
    @DisplayName("UPDATE - Actualizar usuario existente correctamente")
    void testUpdateUserSuccess(){
        when(userDAO.findById(1L)).thenReturn(Optional.of(validUser));
        doNothing().when(userValidate).validateUpdate(1L, updatedUser);
        when(userDAO.update(1L, updatedUser)).thenReturn(Optional.of(updatedUser));

        UserDTO result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Maria actualizado", result.getFullName());
        verify(userDAO).update(1L, updatedUser);
    }

    @Test
    @DisplayName("❌ Intentar actualizar usuario inexistente lanza excepción")
    void  testUpdateUserNotFoundThrowsException(){
        when(userDAO.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(99L, updatedUser));
    }

    // DELETE
    @Test
    @DisplayName("DELETE - Eliminar usuario existente correctamente")
    void testDeleteUserSuccess(){
        when(userDAO.findById(1L)).thenReturn(Optional.of(validUser));
        when(userDAO.deleteById(1L)).thenReturn(true);

        userService.deleteUser(1L);
        verify(userDAO).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE - Eliminar usuario inexistente lanza excepción")
    void testDeleteUserNotFoundThrowsException() {
        when(userDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
        verify(userDAO, never()).deleteById(any());
    }

    // LOGIN
    @Test
    @DisplayName("LOGIN - Login exitoso con credenciales válidas")
    void testLoginSuccess() {
        when(userDAO.finByEmail("juan@example.com")).thenReturn(Optional.of(validUser));

        UserDTO result = userService.login("juan@example.com", "123456");

        assertNotNull(result);
        assertEquals("juan@example.com", result.getEmail());
        verify(userDAO).finByEmail("juan@example.com");
    }

    @Test
    @DisplayName("LOGIN - Login falla con contraseña incorrecta")
    void testLoginInvalidPasswordThrowsException() {
        when(userDAO.finByEmail("juan@example.com")).thenReturn(Optional.of(validUser));

        assertThrows(RuntimeException.class, () -> userService.login("juan@example.com", "wrong"));
        verify(userDAO).finByEmail("juan@example.com");
    }

    @Test
    @DisplayName("LOGIN - Login falla con email no existente")
    void testLoginEmailNotFoundThrowsException() {
        when(userDAO.finByEmail("noexist@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.login("noexist@example.com", "123456"));
        verify(userDAO).finByEmail("noexist@example.com");
    }

}
