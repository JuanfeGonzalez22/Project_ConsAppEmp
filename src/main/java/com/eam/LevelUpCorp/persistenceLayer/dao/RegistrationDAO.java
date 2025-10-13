package com.eam.LevelUpCorp.persistenceLayer.dao;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RegistrationResponseDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.RegistrationEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.RegistrationMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.RegistrationRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class RegistrationDAO {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    //Save
    public RegistrationResponseDTO save(RegistrationDTO registrationDTO){
        RegistrationEntity registrationEntity = registrationMapper.toEntity(registrationDTO);
        RegistrationEntity savedRegistrationEntity = registrationRepository.save(registrationEntity);
        return convertToResponseDTO(savedRegistrationEntity);
    }

    //Search
    public Optional<RegistrationResponseDTO> findById(Long id){
        return registrationRepository.findById(id).map(this::convertToResponseDTO);
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
    public List<RegistrationResponseDTO> findAll(){
        return registrationRepository.findAll().stream().map(this::convertToResponseDTO).toList();
    }

    //Convert entity -> ResponseDTO
    private RegistrationResponseDTO convertToResponseDTO(RegistrationEntity registrationEntity) {

        UserEntity user = userRepository.findById(registrationEntity.getId()).orElse(null);

        CourseEntity course = courseRepository.findById(registrationEntity.getId()).orElse(null);

        return new RegistrationResponseDTO(
                registrationEntity.getId(),
                registrationEntity.getUserId(),
                user != null ? user.getName() : "Unknown",
                user != null ? user.getEmail() : "Unknown",
                registrationEntity.getCourseId(),
                course != null ? course.getTitle() : "Unknown",
                registrationEntity.getProgress(),
                registrationEntity.getEnrollmentDate(),
                registrationEntity.getStatus()
        );

    }
}
