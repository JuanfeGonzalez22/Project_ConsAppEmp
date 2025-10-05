package com.eam.LevelUpCorp.businessLayer.service.impl;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import com.eam.LevelUpCorp.businessLayer.service.RegistrationService;
import com.eam.LevelUpCorp.businessLayer.validate.RegistrationValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.RegistrationDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationDAO registrationDAO;
    private final RegistrationValidate registrationValidate;

    /*
    Method create a Registration.
     */

    @Override
    public RegistrationDTO createRegistration(RegistrationDTO dto) {
        log.info("Creating new registration for {}", dto);
        registrationValidate.validateCreate(dto);
        return registrationDAO.save(dto);
    }

    @Override
    public RegistrationDTO getRegistration(Long id) {
        log.debug("Fetching registration with id {}", id);
        registrationValidate.validateSearch(id);
        return registrationDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        log.debug("Fetching all registrations");
        return registrationDAO.findAll();
    }

    @Override
    public RegistrationDTO updateRegistration(Long id, RegistrationDTO dto) {
        log.info("Updating registration with id {}", id);
        registrationValidate.validateUpdate(id, dto);
        return registrationDAO.update(id, dto)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
    }

    @Override
    public void deleteRegistration(Long id) {
        log.warn("Deleting registration with id {}", id);
        registrationValidate.validateDelete(id);
        registrationDAO.deleteById(id);
    }
}
