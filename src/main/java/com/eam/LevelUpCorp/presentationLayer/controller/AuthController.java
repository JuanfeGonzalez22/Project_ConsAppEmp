package com.eam.LevelUpCorp.presentationLayer.controller;


import com.eam.LevelUpCorp.businessLayer.dto.LoginDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserRegisterDTO;
import com.eam.LevelUpCorp.businessLayer.service.UserService;
import com.eam.LevelUpCorp.businessLayer.validate.UserValidate;
import io.swagger.v3.oas.annotations.Operation;
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
import com.eam.LevelUpCorp.security.JwtUtil;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticación", description = "Registro y login de usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final UserValidate userValidate;
    private final JwtUtil jwtUtil; // ← INYECTA JwtUtil

    /**
     * Registrar un nuevo usuario (Aprendiz o Instructor)
     */
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> registerUser( // ← Cambia a ResponseEntity<?>
            @RequestBody UserRegisterDTO registerDTO
    ) {
        log.info("POST /api/v1/auth/register - Registrando usuario: {}", registerDTO.getEmail());
        try {
            // Validar datos del registro
            userValidate.validateCreate(convertToUserDTO(registerDTO));

            // Mapear UserRegisterDTO → UserDTO
            UserDTO userDTO = convertToUserDTO(registerDTO);

            // Crear usuario (TU LÓGICA ACTUAL)
            UserDTO createdUser = userService.createUser(userDTO);

            // ✅ NUEVO: Generar token JWT
            String token = jwtUtil.generateToken(createdUser.getEmail());

            log.info("Usuario registrado con ID: {}", createdUser.getId());
            
            // ✅ NUEVO: Devolver token + user data
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "token", token,
                "user", createdUser,
                "message", "Usuario registrado exitosamente"
            ));
            
        } catch (IllegalArgumentException e) {
            log.warn("Error al registrar usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Login de usuario
     */
    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Autentica un usuario con email y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<?> login( // ← Cambia a ResponseEntity<?>
            @RequestBody LoginDTO loginDTO
    ) {
        log.info("POST /api/v1/auth/login - Intento de login: {}", loginDTO.getEmail());
        try {
            // Validar datos de login
            userValidate.validateLogin(convertToUserDTO(loginDTO));

            // Login (TU LÓGICA ACTUAL)
            UserDTO user = userService.login(loginDTO.getEmail(), loginDTO.getPassword());

            // ✅ NUEVO: Generar token JWT
            String token = jwtUtil.generateToken(user.getEmail());

            log.info("Login exitoso: {}", loginDTO.getEmail());
            
            // ✅ NUEVO: Devolver token + user data
            return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user,
                "message", "Login exitoso"
            ));
            
        } catch (RuntimeException e) {
            log.warn("Login fallido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 🔁 TUS MÉTODOS convertToUserDTO SE MANTIENEN IGUAL
    private UserDTO convertToUserDTO(UserRegisterDTO registerDTO) {
        UserDTO dto = new UserDTO();
        dto.setFullName(registerDTO.getName());
        dto.setEmail(registerDTO.getEmail());
        dto.setPassword(registerDTO.getPassword());
        dto.setRole(registerDTO.getRole());
        return dto;
    }

    private UserDTO convertToUserDTO(LoginDTO loginDTO) {
        UserDTO dto = new UserDTO();
        dto.setEmail(loginDTO.getEmail());
        dto.setPassword(loginDTO.getPassword());
        return dto;
    }
}