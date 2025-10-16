package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.ModuleDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ModuleEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ModuleMapper {

    /*
    Converts a list of ModuleEntity to list of ModuleDTO.
     */
    List<ModuleDTO> toDTOList(List<ModuleEntity> moduleEntities);

    /*
    Converts a single ModuleEntity to ModuleDTO
     */
    ModuleDTO toDTO(ModuleEntity moduleEntity);

    /*
    Converts a ModuleDTO to ModuleEntity
     */
    @Mapping(target = "id", ignore = true)
    ModuleEntity toEntity(ModuleDTO moduleDTO);

    /*
    Updates an existing ModuleEntity using data from a ModuleDTO.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ModuleDTO moduleDTO, @MappingTarget ModuleEntity moduleEntity);
}
