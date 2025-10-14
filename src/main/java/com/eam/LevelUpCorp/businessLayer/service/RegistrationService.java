package com.eam.LevelUpCorp.businessLayer.service;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationResponseDTO;

import java.util.List;

public interface RegistrationService {

    //Create
    RegistrationResponseDTO createRegistration(RegistrationDTO registrationDTO);

    //Search for ID
    RegistrationResponseDTO getRegistration(Long id);

    //Get everything all Registration.
    List<RegistrationResponseDTO> getAllRegistrations();

    //Delete for ID.
    void deleteRegistration(Long id);

    //Update.
    RegistrationDTO updateRegistration(Long id, RegistrationDTO registrationDTO);
}
