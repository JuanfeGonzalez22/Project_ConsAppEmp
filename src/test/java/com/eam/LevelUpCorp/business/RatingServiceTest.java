package com.eam.LevelUpCorp.business;


import com.eam.LevelUpCorp.businessLayer.dto.*;
import com.eam.LevelUpCorp.businessLayer.service.impl.RatingServiceImpl;
import com.eam.LevelUpCorp.persistenceLayer.dao.AnswerDAO;
import com.eam.LevelUpCorp.persistenceLayer.dao.RatingDAO;
import com.eam.LevelUpCorp.persistenceLayer.dao.UserRatingDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para RatingServiceImpl
 *
 * OBJETIVO: Probar el sistema de gamificación y recompensas
 * - CRUD de ratings/logros
 * - Asignación de logros a usuarios
 * - Verificación automática de logros
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RatingService - Unit Tests")
public class RatingServiceTest {

    @Mock
    private RatingDAO ratingDAO;

    @Mock
    private UserRatingDAO userRatingDAO;

    @Mock
    private AnswerDAO answerDAO;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private RatingDTO validRatingDTO;
    private RatingResponseDTO validRatingResponseDTO;
    private Long existingRatingId;
    private Long nonExistingRatingId;
    private Long existingUserId;
    private String existingCode;
    private String nonExistingCode;


    @BeforeEach
    void setUp() {
        existingRatingId = 1L;
        nonExistingRatingId = 999L;
        existingUserId = 100L;
        existingCode = "RWD-001";
        nonExistingCode = "RWD-999";


        validRatingDTO = new RatingDTO(
                "Mejor desempeño",
                "Completar 3 cursos",
                "medal.png",
                "RWD-001"
        );

        validRatingResponseDTO = new RatingResponseDTO(
                existingRatingId,
                "Mejor desempeño",
                "Completar 3 cursos",
                "medal.png",
                "RWD-001"
        );
    }


    @Test
    @DisplayName("CREATE - Cuando datos son válidos y código no existe, debe crear rating exitosamente")
    void create_WithValidDataAndUniqueCode_ShouldReturnRatingResponseDTO() {

        when(ratingDAO.existsByCode(validRatingDTO.getCode())).thenReturn(false);
        when(ratingDAO.save(validRatingDTO)).thenReturn(validRatingResponseDTO);


        RatingResponseDTO result = ratingService.create(validRatingDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingRatingId);
        assertThat(result.getName()).isEqualTo("Mejor desempeño");
        assertThat(result.getCode()).isEqualTo("RWD-001");

        verify(ratingDAO, times(1)).existsByCode("RWD-001");
        verify(ratingDAO, times(1)).save(validRatingDTO);
    }

    @Test
    @DisplayName("CREATE - Cuando código ya existe, debe lanzar RuntimeException")
    void create_WithExistingCode_ShouldThrowRuntimeException() {
        when(ratingDAO.existsByCode(validRatingDTO.getCode())).thenReturn(true);

        assertThatThrownBy(() -> ratingService.create(validRatingDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("El código ya existe");

        verify(ratingDAO, times(1)).existsByCode("RWD-001");
        verify(ratingDAO, never()).save(any(RatingDTO.class));
    }


    @Test
    @DisplayName("GET BY ID - Cuando ID existe, debe retornar RatingResponseDTO")
    void getRatingById_WithExistingId_ShouldReturnRatingResponseDTO() {
        when(ratingDAO.findById(existingRatingId)).thenReturn(Optional.of(validRatingResponseDTO));

        RatingResponseDTO result = ratingService.getRatingById(existingRatingId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingRatingId);
        assertThat(result.getName()).isEqualTo("Mejor desempeño");

        verify(ratingDAO, times(1)).findById(existingRatingId);
    }

    @Test
    @DisplayName("GET BY ID - Cuando ID no existe, debe lanzar RuntimeException")
    void getRatingById_WithNonExistingId_ShouldThrowRuntimeException() {
        when(ratingDAO.findById(nonExistingRatingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ratingService.getRatingById(nonExistingRatingId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Gamificación no encontrada");

        verify(ratingDAO, times(1)).findById(nonExistingRatingId);
    }


    @Test
    @DisplayName("GET BY CODE - Cuando código existe, debe retornar RatingResponseDTO")
    void getRatingByCode_WithExistingCode_ShouldReturnRatingResponseDTO() {
        when(ratingDAO.findByCode(existingCode)).thenReturn(Optional.of(validRatingResponseDTO));

        RatingResponseDTO result = ratingService.getRatingByCode(existingCode);

        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo(existingCode);
        assertThat(result.getName()).isEqualTo("Mejor desempeño");

        verify(ratingDAO, times(1)).findByCode(existingCode);
    }

    @Test
    @DisplayName("GET BY CODE - Cuando código no existe, debe lanzar RuntimeException")
    void getRatingByCode_WithNonExistingCode_ShouldThrowRuntimeException() {


        when(ratingDAO.findByCode(nonExistingCode)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ratingService.getRatingByCode(nonExistingCode))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Gamificación no encontrada");

        verify(ratingDAO, times(1)).findByCode(nonExistingCode);
    }


    @Test
    @DisplayName("GET ALL - Cuando hay ratings, debe retornar lista de RatingResponseDTO")
    void getAllRatings_WithExistingRatings_ShouldReturnList() {


        List<RatingResponseDTO> ratings = Arrays.asList(
                validRatingResponseDTO,
                new RatingResponseDTO(
                        2L,
                        "Logro Avanzado",
                        "Completar 5 cursos",
                        "star.png",
                        "RWD-002")
        );

        when(ratingDAO.findAll()).thenReturn(ratings);

        List<RatingResponseDTO> result = ratingService.getAllRatings();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting("name")
                .containsExactly("Mejor desempeño", "Logro Avanzado");

        verify(ratingDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("GET ALL - Cuando no hay ratings, debe lanzar RuntimeException")
    void getAllRatings_WithNoRatings_ShouldThrowRuntimeException() {
        when(ratingDAO.findAll()).thenReturn(Arrays.asList());


        assertThatThrownBy(() -> ratingService.getAllRatings())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No hay gamificaciones disponibles");

        verify(ratingDAO, times(1)).findAll();
    }


    @Test
    @DisplayName("UPDATE - Cuando datos son válidos, debe actualizar rating exitosamente")
    void updateRating_WithValidData_ShouldReturnUpdatedRating() {

        RatingDTO updateDTO = new RatingDTO(
                "Nuevo Nombre",
                "Nuevo Criterio",
                "new_icon.png",
                "RWD-001");
        RatingResponseDTO updatedResponse = new RatingResponseDTO(existingRatingId, "Nuevo Nombre", "Nuevo Criterio", "new_icon.png", "RWD-001");

        when(ratingDAO.findById(existingRatingId)).thenReturn(Optional.of(validRatingResponseDTO));
        when(ratingDAO.findByCode("RWD-001")).thenReturn(Optional.of(validRatingResponseDTO));
        when(ratingDAO.update(eq(existingRatingId), any(RatingDTO.class))).thenReturn(Optional.of(updatedResponse));

        RatingResponseDTO result = ratingService.updateRating(existingRatingId, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Nuevo Nombre");
        assertThat(result.getCriterion()).isEqualTo("Nuevo Criterio");

        verify(ratingDAO, times(1)).findById(existingRatingId);
        verify(ratingDAO, times(1)).update(eq(existingRatingId), any(RatingDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Cuando código existe en otro rating, debe lanzar RuntimeException")
    void updateRating_WithDuplicateCodeInOtherRating_ShouldThrowRuntimeException() {
        RatingDTO updateDTO = new RatingDTO(
                "Nombre",
                "Criterio",
                "icon.png",
                "RWD-002");
        RatingResponseDTO otherRating = new RatingResponseDTO(
                2L,
                "Otro Rating",
                "Criterio",
                "icon.png",
                "RWD-002");

        when(ratingDAO.findById(existingRatingId)).thenReturn(Optional.of(validRatingResponseDTO));
        when(ratingDAO.findByCode("RWD-002")).thenReturn(Optional.of(otherRating));

        assertThatThrownBy(() -> ratingService.updateRating(existingRatingId, updateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("El código ya existe en otra gamificación");

        verify(ratingDAO, never()).update(anyLong(), any(RatingDTO.class));
    }


    @Test
    @DisplayName("DELETE - Cuando ID existe, debe eliminar rating exitosamente")
    void deleteRating_WithExistingId_ShouldDeleteSuccessfully() {
        when(ratingDAO.findById(existingRatingId)).thenReturn(Optional.of(validRatingResponseDTO));
        when(ratingDAO.deleteById(existingRatingId)).thenReturn(true);

        assertThatCode(() -> ratingService.deleteRating(existingRatingId))
                .doesNotThrowAnyException();

        verify(ratingDAO, times(1)).findById(existingRatingId);
        verify(ratingDAO, times(1)).deleteById(existingRatingId);
    }

    @Test
    @DisplayName("DELETE - Cuando eliminación falla en DAO, debe lanzar RuntimeException")
    void deleteRating_WhenDAOFails_ShouldThrowRuntimeException() {

        when(ratingDAO.findById(existingRatingId)).thenReturn(Optional.of(validRatingResponseDTO));
        when(ratingDAO.deleteById(existingRatingId)).thenReturn(false);

        assertThatThrownBy(() -> ratingService.deleteRating(existingRatingId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error eliminando la gamificación");

        verify(ratingDAO, times(1)).deleteById(existingRatingId);
    }


    @Test
    @DisplayName("ASIGNAR LOGRO - Cuando usuario no tiene el logro, debe asignarlo y retornar true")
    void asignarLogroUsuario_WhenUserDoesNotHaveAchievement_ShouldAssignAndReturnTrue() {

        String achievementCode = "PRIMERA_RESPUESTA";

        when(ratingDAO.findByCode(achievementCode)).thenReturn(Optional.of(validRatingResponseDTO));
        when(userRatingDAO.existsByUserIdAndRatingId(existingUserId, existingRatingId)).thenReturn(false);

        boolean result = ratingService.asignarLogroUsuario(existingUserId, achievementCode);

        assertThat(result).isTrue();

        verify(ratingDAO, times(1)).findByCode(achievementCode);
        verify(userRatingDAO, times(1)).existsByUserIdAndRatingId(existingUserId, existingRatingId);
        verify(userRatingDAO, times(1)).save(any(UserRatingDTO.class));
    }

    @Test
    @DisplayName("ASIGNAR LOGRO - Cuando usuario ya tiene el logro, debe retornar false")
    void asignarLogroUsuario_WhenUserAlreadyHasAchievement_ShouldReturnFalse() {

        String achievementCode = "PRIMERA_RESPUESTA";

        when(ratingDAO.findByCode(achievementCode)).thenReturn(Optional.of(validRatingResponseDTO));
        when(userRatingDAO.existsByUserIdAndRatingId(existingUserId, existingRatingId)).thenReturn(true);

        boolean result = ratingService.asignarLogroUsuario(existingUserId, achievementCode);

        assertThat(result).isFalse();

        verify(userRatingDAO, never()).save(any(UserRatingDTO.class));
    }


    @Test
    @DisplayName("GET LOGROS USUARIO - Cuando usuario tiene logros, debe retornar lista")
    void getLogrosUsuario_WhenUserHasAchievements_ShouldReturnList() {

        UserRatingResponseDTO userRating = new UserRatingResponseDTO(
                1L,
                existingUserId,
                existingRatingId,
                null);
        List<UserRatingResponseDTO> userRatings = Arrays.asList(userRating);

        when(userRatingDAO.findByUserId(existingUserId)).thenReturn(userRatings);
        when(ratingDAO.findById(existingRatingId)).thenReturn(Optional.of(validRatingResponseDTO));

        List<RatingResponseDTO> result = ratingService.getLogrosUsuario(existingUserId);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Mejor desempeño");

        verify(userRatingDAO, times(1)).findByUserId(existingUserId);
        verify(ratingDAO, times(1)).findById(existingRatingId);
    }


    @Test
    @DisplayName("VERIFICAR LOGROS - Con acción RESPUESTA_ENVIADA, debe verificar logros de respuestas")
    void verificarLogrosAutomatic_WithRespuestaEnviadaAction_ShouldVerifyAnswerAchievements() {
        Long evaluationId = 50L;

        AnswerResponseDTO perfectAnswer = new AnswerResponseDTO(
                1L,
                LocalDateTime.now(),
                evaluationId,
                existingUserId,
                100.0
        );

        when(answerDAO.countByUserId(existingUserId)).thenReturn(1L);
        when(answerDAO.findByEvaluationIdAndUserId(evaluationId, existingUserId))
                .thenReturn(Optional.of(perfectAnswer));

        when(ratingDAO.findByCode(anyString())).thenReturn(Optional.of(validRatingResponseDTO));
        when(userRatingDAO.existsByUserIdAndRatingId(anyLong(), anyLong())).thenReturn(false);

        ratingService.verificarLogrosAutomatic(existingUserId, "RESPUESTA_ENVIADA", evaluationId);

        verify(answerDAO, times(1)).countByUserId(existingUserId);
        verify(answerDAO, times(1)).findByEvaluationIdAndUserId(evaluationId, existingUserId);
        verify(ratingDAO, atLeastOnce()).findByCode(anyString());
    }

    @Test
    @DisplayName("VERIFICAR LOGROS - Con acción EVALUACION_APROBADA, debe verificar logros de evaluaciones")
    void verificarLogrosAutomatic_WithEvaluationAprobadaAction_ShouldVerifyEvaluationAchievements() {

        Double score = 95.0;
        when(answerDAO.countByUserIdAndScoreGreaterThanEqual(existingUserId, 95.0)).thenReturn(5L);

        when(ratingDAO.findByCode(anyString())).thenReturn(Optional.of(validRatingResponseDTO));
        when(userRatingDAO.existsByUserIdAndRatingId(anyLong(), anyLong())).thenReturn(false);

        ratingService.verificarLogrosAutomatic(existingUserId, "EVALUACION_APROBADA", score);

        verify(answerDAO, times(1)).countByUserIdAndScoreGreaterThanEqual(existingUserId, 95.0);
        verify(ratingDAO, atLeastOnce()).findByCode(anyString());
    }

    @Test
    @DisplayName("VERIFICAR LOGROS - Con acción desconocida, debe loguear advertencia")
    void verificarLogrosAutomatic_WithUnknownAction_ShouldLogWarning() {

        ratingService.verificarLogrosAutomatic(existingUserId, "ACCION_DESCONOCIDA", null);

        verify(answerDAO, never()).countByUserId(anyLong());
        verify(answerDAO, never()).findByEvaluationIdAndUserId(anyLong(), anyLong());
    }


    @Test
    @DisplayName("VERIFICAR LOGROS - Primera respuesta debe asignar logro PRIMERA_RESPUESTA")
    void verificarLogros_WithFirstAnswer_ShouldAssignPrimeraRespuestaAchievement() {
        Long evaluationId = 50L;
        when(answerDAO.countByUserId(existingUserId)).thenReturn(1L);
        when(answerDAO.findByEvaluationIdAndUserId(evaluationId, existingUserId))
                .thenReturn(Optional.of(new AnswerResponseDTO(1L, LocalDateTime.now(), evaluationId, existingUserId, 85.0)));

        when(ratingDAO.findByCode("PRIMERA_RESPUESTA")).thenReturn(Optional.of(validRatingResponseDTO));
        when(userRatingDAO.existsByUserIdAndRatingId(existingUserId, existingRatingId)).thenReturn(false);

        ratingService.verificarLogrosAutomatic(existingUserId, "RESPUESTA_ENVIADA", evaluationId);

        verify(ratingDAO, times(1)).findByCode("PRIMERA_RESPUESTA");
    }

    @Test
    @DisplayName("VERIFICAR LOGROS - Respuesta perfecta (100%) debe asignar logro PERFECTO_EVALUACION")
    void verificarLogros_WithPerfectScore_ShouldAssignPerfectoEvaluacionAchievement() {
        Long evaluationId = 50L;

        AnswerResponseDTO perfectAnswer = new AnswerResponseDTO(
                1L, LocalDateTime.now(), evaluationId, existingUserId, 100.0
        );

        when(answerDAO.countByUserId(existingUserId)).thenReturn(5L);
        when(answerDAO.findByEvaluationIdAndUserId(evaluationId, existingUserId))
                .thenReturn(Optional.of(perfectAnswer));

        when(ratingDAO.findByCode(anyString())).thenReturn(Optional.of(validRatingResponseDTO));
        when(userRatingDAO.existsByUserIdAndRatingId(anyLong(), anyLong())).thenReturn(false);

        ratingService.verificarLogrosAutomatic(existingUserId, "RESPUESTA_ENVIADA", evaluationId);

        verify(ratingDAO, atLeast(1)).findByCode(anyString());
    }


    @Test
    @DisplayName("EXISTS BY CODE - Cuando código existe, debe retornar true")
    void existsByCode_WithExistingCode_ShouldReturnTrue() {
        when(ratingDAO.existsByCode(existingCode)).thenReturn(true);

        boolean result = ratingService.existsByCode(existingCode);

        assertThat(result).isTrue();

        verify(ratingDAO, times(1)).existsByCode(existingCode);
    }

    @Test
    @DisplayName("GET RATINGS BY NAME - Cuando hay ratings con el nombre, debe retornar lista")
    void getRatingsByName_WithMatchingRatings_ShouldReturnList() {
        String searchName = "desempeño";
        List<RatingResponseDTO> ratings = Arrays.asList(validRatingResponseDTO);

        when(ratingDAO.findByName(searchName)).thenReturn(ratings);

        List<RatingResponseDTO> result = ratingService.getRatingsByName(searchName);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).contains("desempeño");

        verify(ratingDAO, times(1)).findByName(searchName);
    }

    @Test
    @DisplayName("GET RATINGS BY NAME - Cuando no hay ratings con el nombre, debe lanzar RuntimeException")
    void getRatingsByName_WithNoMatchingRatings_ShouldThrowRuntimeException() {
        String searchName = "inexistente";
        when(ratingDAO.findByName(searchName)).thenReturn(Arrays.asList());

        assertThatThrownBy(() -> ratingService.getRatingsByName(searchName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se encontraron gamificaciones con nombre");

        verify(ratingDAO, times(1)).findByName(searchName);
    }
}