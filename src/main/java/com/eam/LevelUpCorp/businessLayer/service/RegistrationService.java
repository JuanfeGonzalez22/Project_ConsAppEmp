package com.eam.LevelUpCorp.businessLayer.service;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;

import java.util.List;

public interface RegistrationService {

    //Create
    RegistrationDTO createRegistration(RegistrationDTO registrationDTO);

    //Search for ID
    RegistrationDTO getRegistration(Long id);

    //Get everything all Registration.
    List<RegistrationDTO> getAllRegistrations();

    //Delete for ID.
    void deleteRegistration(Long id);

    //Update.
    RegistrationDTO updateRegistration(Long id, RegistrationDTO registrationDTO);
}
