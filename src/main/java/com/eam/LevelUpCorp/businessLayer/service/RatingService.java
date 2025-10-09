package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.RatingDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RatingResponseDTO;

import java.util.List;

public interface RatingService {


    // Create
    RatingResponseDTO create(RatingDTO createDTO);

    // Search by ID
    RatingResponseDTO getRatingById(Long id);

    // Search by códe
    RatingResponseDTO getRatingByCode(String code);

    // Search for everyone
    List<RatingResponseDTO> getAllRatings();

    // Update
    RatingResponseDTO updateRating(Long id, RatingDTO createDTO);

    // Delete
    void deleteRating(Long id);

    // Search by name
    List<RatingResponseDTO> getRatingsByName(String name);

    // Check if códe exists
    boolean existsByCode(String code);


    boolean asignarLogroUsuario(Long userId, String codeLogro);


    List<RatingResponseDTO> getLogrosUsuario(Long userId);

    // Verificar y asignar logros automáticamente
    void verificarLogrosAutomatic(Long userId, String action, Object datosAction);



}
