package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.RegistrationDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.RegistrationEntity;
import jakarta.servlet.Registration;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)

public interface RegistrationMapper {


    /*
     Converts a list of RegistrationEntity to a list of RegistrationDTOs.
     */
    List<RegistrationDTO> toDTOList(List<RegistrationEntity> registrationEntities);

    /*
      Converts a single RegistrationEntity to RegistrationDTO.
     */
    RegistrationDTO toDTO(RegistrationEntity registrationEntity);

    /*
      Converts an RegistrationDTO to RegistrationEntity.
      Used for creating new registrations in the db.
     */
    @Mapping(target = "id", ignore = true) // id is auto-generated
    RegistrationEntity toEntity(RegistrationDTO registrationDTO);

    /*
     Updates an existing RegistrationEntity using data from an RegistrationDTO.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(RegistrationDTO registrationDTO, @MappingTarget RegistrationEntity registrationEntity);
}

