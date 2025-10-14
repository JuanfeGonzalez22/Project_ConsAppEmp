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


    ProgressHistoryDTO toDTO(ProgressHistoryEntity progressHistoryEntity);

    List<ProgressHistoryDTO> toDTOList(List<ProgressHistoryEntity> progressHistoryEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accessDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProgressHistoryEntity toEntity(ProgressHistoryDTO progressHistoryDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accessDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProgressHistoryDTO progressHistoryDTO, @MappingTarget ProgressHistoryEntity progressHistoryEntity);

}
