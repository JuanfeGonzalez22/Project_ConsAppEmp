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

    // Entity to DTO
    RegistrationDTO toDTO(RegistrationEntity registrationEntity);

    // DTO to Entity
    @Mapping(target = "id", ignore = true) // id se genera en BD
    @Mapping(target = "enrollmentDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "progress", constant = "0.0")
    RegistrationEntity toEntity(RegistrationDTO registrationDTO);

    // Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollmentDate", ignore = true)
    @Mapping(target = "progress", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(RegistrationDTO registrationDTO, @MappingTarget RegistrationEntity registrationEntity);
}

