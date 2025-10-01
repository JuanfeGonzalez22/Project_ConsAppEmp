package com.eam.LevelUpCorp.persistenceLayer.dao;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.RegistrationEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.RegistrationMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class RegistrationDAO {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    //Save
    public RegistrationDTO save(RegistrationDTO registrationDTO){
        RegistrationEntity registrationEntity = registrationMapper.toEntity(registrationDTO);
        RegistrationEntity savedRegistrationEntity = registrationRepository.save(registrationEntity);
        return registrationMapper.toDTO(savedRegistrationEntity);
    }

    //Search
    public Optional<RegistrationDTO> findById(Long id){
        return registrationRepository.findById(id).map(registrationMapper::toDTO);
    }

    //Update
    public Optional<RegistrationDTO> update(Long id, RegistrationDTO registrationDTO){
        return registrationRepository.findById(id).map(existingEntity -> {registrationMapper.updateEntityFromDTO(registrationDTO, existingEntity);
            RegistrationEntity updatedEntity = registrationRepository.save(existingEntity);
            return registrationMapper.toDTO(updatedEntity);
        });
    }

    //Delete
    public boolean deleteById(Long id){
        if (registrationRepository.existsById(id)){
            registrationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //All Registration
    public List<RegistrationDTO> findAll(){
        return registrationRepository.findAll().stream().map(registrationMapper::toDTO).toList();
    }
}
