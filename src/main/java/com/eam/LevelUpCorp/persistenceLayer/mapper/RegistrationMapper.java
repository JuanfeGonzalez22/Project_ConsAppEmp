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

    // Entity -> DTO: MapStruct mapea automáticamente los campos que coinciden
    RegistrationDTO toDTO(RegistrationEntity registrationEntity);

    // DTO -> Entity: Fecha automática, progress por defecto
    @Mapping(target = "id", ignore = true) // id se genera en BD
    @Mapping(target = "enrollmentDate", expression = "java(java.time.LocalDate.now())") // ✅ Fecha automática
    @Mapping(target = "progress", constant = "0.0") // ✅ Progress siempre empieza en 0
    RegistrationEntity toEntity(RegistrationDTO registrationDTO);

    // Update: Solo actualizar campos permitidos
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollmentDate", ignore = true) // ✅ No cambiar fecha original
    @Mapping(target = "progress", ignore = true) // ✅ No cambiar progress desde DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(RegistrationDTO registrationDTO, @MappingTarget RegistrationEntity registrationEntity);
}

