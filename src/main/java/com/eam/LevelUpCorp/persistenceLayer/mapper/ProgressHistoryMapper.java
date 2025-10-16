package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.ProgressHistoryDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ProgressHistoryEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ProgressHistoryMapper {

    //Entity to DTO
    ProgressHistoryDTO toDTO(ProgressHistoryEntity progressHistoryEntity);


    //List of Entity to List of DTO
    List<ProgressHistoryDTO> toDTOList(List<ProgressHistoryEntity> progressHistoryEntityList);

    //DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accessDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProgressHistoryEntity toEntity(ProgressHistoryDTO progressHistoryDTO);


    //Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accessDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProgressHistoryDTO progressHistoryDTO, @MappingTarget ProgressHistoryEntity progressHistoryEntity);

}
