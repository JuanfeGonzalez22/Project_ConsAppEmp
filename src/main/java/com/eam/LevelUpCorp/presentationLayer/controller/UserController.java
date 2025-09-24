package com.eam.LevelUpCorp.presentationLayer.controller;

import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.businessLayer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones de usuarios
 *
 * RESPONSABILIDADES:
 * - Exponer endpoints REST para usuarios
 * - Validar requests HTTP
 * - Manejar responses y códigos de estado
 * - Documentar API con Swagger/OpenAPI
 * - Delegar lógica de negocio al Service
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "Operaciones CRUD para gestión de usuarios y autenticación")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    /**
     * Crear un nuevo usuario
     */
    @PostMapping
    @Operation(
            summary = "Crear nuevo usuario",
            description = "Crea un nuevo usuario en el sistema con validación de email único y contraseña segura"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos (email duplicado, contraseña muy corta, campos requeridos faltantes)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<?> createUser(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @RequestBody UserDTO userDTO
    ) {
        log.info("POST /api/v1/users - Creando usuario: {}", userDTO.getEmail());
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            // No retornamos la contraseña en la respuesta por seguridad
            createdUser.setPassword(null);
            log.info("Usuario creado exitosamente con ID: {}", createdUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            log.warn("Error de validación al crear usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Error interno al crear usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * Obtener usuario por ID
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuario por ID",
            description = "Obtiene la información completa de un usuario específico (sin contraseña)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado"
            )
    })
    public ResponseEntity<?> getUserById(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id
    ) {
        log.debug("GET /api/v1/users/{} - Buscando usuario", id);
        try {
            UserDTO user = userService.getUserById(id);
            // No retornamos la contraseña por seguridad
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            log.warn("ID inválido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("Usuario no encontrado con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener todos los usuarios
     */
    @GetMapping
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Obtiene la lista completa de usuarios registrados en el sistema (sin contraseñas)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            )
    })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.debug("GET /api/v1/users - Obteniendo todos los usuarios");
        List<UserDTO> users = userService.getAllUsers();
        // Remover contraseñas por seguridad
        users.forEach(user -> user.setPassword(null));
        log.debug("Se encontraron {} usuarios", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * Actualizar usuario existente
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza la información de un usuario existente. Se pueden actualizar todos los campos excepto el ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o ID inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado"
            )
    })
    public ResponseEntity<?> updateUser(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos a actualizar del usuario", required = true)
            @RequestBody UserDTO userDTO
    ) {
        log.info("PUT /api/v1/users/{} - Actualizando usuario", id);
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            // No retornamos la contraseña por seguridad
            updatedUser.setPassword(null);
            log.info("Usuario actualizado exitosamente ID: {}", id);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            log.warn("Error de validación al actualizar usuario ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("Usuario no encontrado para actualizar ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar usuario
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema de forma permanente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuario eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado"
            )
    })
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        log.info("DELETE /api/v1/users/{} - Eliminando usuario", id);
        try {
            userService.deleteUser(id);
            log.info("Usuario eliminado exitosamente ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.warn("ID inválido para eliminar: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("Usuario no encontrado para eliminar ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Login de usuario
     */
    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuario",
            description = "Autentica un usuario con email y contraseña. Retorna la información del usuario si las credenciales son válidas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de login inválidos (email o contraseña faltantes)"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciales incorrectas"
            )
    })
    public ResponseEntity<?> login(
            @Parameter(description = "Email del usuario", required = true, example = "john.smith@company.com")
            @RequestParam String email,
            @Parameter(description = "Contraseña del usuario", required = true)
            @RequestParam String password
    ) {
        log.info("POST /api/v1/users/login - Intento de login para: {}", email);
        try {
            UserDTO authenticatedUser = userService.login(email, password);
            // No retornamos la contraseña por seguridad
            authenticatedUser.setPassword(null);
            log.info("Login exitoso para usuario: {}", email);
            return ResponseEntity.ok(authenticatedUser);
        } catch (IllegalArgumentException e) {
            log.warn("Datos de login inválidos: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("Credenciales incorrectas para email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    /**
     * Buscar usuarios por rol
     */
    @GetMapping("/role/{role}")
    @Operation(
            summary = "Buscar usuarios por rol",
            description = "Obtiene todos los usuarios que tienen un rol específico (ADMIN, INSTRUCTOR, USER)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuarios encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Rol inválido"
            )
    })
    public ResponseEntity<?> getUsersByRole(
            @Parameter(description = "Rol a buscar", required = true, example = "USER")
            @PathVariable String role
    ) {
        log.debug("GET /api/v1/users/role/{} - Buscando usuarios por rol", role);
        try {
            // Validar que el rol sea válido
            if (role == null || role.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El rol no puede estar vacío");
            }

            List<UserDTO> users = userService.getAllUsers()
                    .stream()
                    .filter(user -> role.equalsIgnoreCase(user.getRole()))
                    .toList();

            // Remover contraseñas por seguridad
            users.forEach(user -> user.setPassword(null));

            log.debug("Se encontraron {} usuarios con rol: {}", users.size(), role);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Error al buscar usuarios por rol: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error al buscar usuarios por rol");
        }
    }

    /**
     * Buscar usuarios por departamento
     */
    @GetMapping("/department/{department}")
    @Operation(
            summary = "Buscar usuarios por departamento",
            description = "Obtiene todos los usuarios que pertenecen a un departamento específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuarios encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Departamento inválido"
            )
    })
    public ResponseEntity<?> getUsersByDepartment(
            @Parameter(description = "Departamento a buscar", required = true, example = "Human Resources")
            @PathVariable String department
    ) {
        log.debug("GET /api/v1/users/department/{} - Buscando usuarios por departamento", department);
        try {
            if (department == null || department.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El departamento no puede estar vacío");
            }

            List<UserDTO> users = userService.getAllUsers()
                    .stream()
                    .filter(user -> department.equalsIgnoreCase(user.getDepartment()))
                    .toList();

            // Remover contraseñas por seguridad
            users.forEach(user -> user.setPassword(null));

            log.debug("Se encontraron {} usuarios en departamento: {}", users.size(), department);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Error al buscar usuarios por departamento: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error al buscar usuarios por departamento");
        }
    }

    /**
     * Buscar usuarios por nombre
     */
    @GetMapping("/search")
    @Operation(
            summary = "Buscar usuarios por nombre",
            description = "Busca usuarios que contengan el texto especificado en su nombre completo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda realizada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parámetro de búsqueda inválido"
            )
    })
    public ResponseEntity<?> searchUsersByName(
            @Parameter(description = "Texto a buscar en el nombre", required = true, example = "John")
            @RequestParam String name
    ) {
        log.debug("GET /api/v1/users/search?name={} - Buscando usuarios por nombre", name);
        try {
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El parámetro de búsqueda no puede estar vacío");
            }

            List<UserDTO> users = userService.getAllUsers()
                    .stream()
                    .filter(user -> user.getFullName() != null &&
                            user.getFullName().toLowerCase().contains(name.toLowerCase()))
                    .toList();

            // Remover contraseñas por seguridad
            users.forEach(user -> user.setPassword(null));

            log.debug("Se encontraron {} usuarios con nombre conteniendo: {}", users.size(), name);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Error al buscar usuarios por nombre: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error en la búsqueda");
        }
    }

    /**
     * Verificar disponibilidad de email
     */
    @GetMapping("/email/{email}/available")
    @Operation(
            summary = "Verificar disponibilidad de email",
            description = "Verifica si un email está disponible para registro (no está siendo usado por otro usuario)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Verificación realizada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email inválido"
            )
    })
    public ResponseEntity<?> isEmailAvailable(
            @Parameter(description = "Email a verificar", required = true, example = "nuevo@company.com")
            @PathVariable String email
    ) {
        log.debug("GET /api/v1/users/email/{}/available - Verificando disponibilidad", email);
        try {
            if (email == null || email.trim().isEmpty() || !email.contains("@")) {
                return ResponseEntity.badRequest().body("Email inválido");
            }

            // Intentar buscar el usuario por email
            try {
                userService.getAllUsers()
                        .stream()
                        .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                        .findFirst()
                        .orElseThrow();

                // Si llegamos aquí, el email ya existe
                return ResponseEntity.ok(false);
            } catch (Exception e) {
                // Si no se encuentra, está disponible
                return ResponseEntity.ok(true);
            }
        } catch (Exception e) {
            log.error("Error al verificar disponibilidad de email: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error en la verificación");
        }
    }
}