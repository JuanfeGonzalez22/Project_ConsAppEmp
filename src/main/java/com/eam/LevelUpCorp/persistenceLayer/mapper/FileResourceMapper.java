package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.FileResourceDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.FileResourceEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileResourceMapper {



    // Convert FileResourceEntity to FileResourceDTO
    FileResourceDTO toDTO(FileResourceEntity fileResourceEntity);

    // Convert List<FileResourceEntity> to List<FileResourceDTO>
    List<FileResourceDTO> toDTOList(List<FileResourceEntity> fileResourceEntities);

    // Convert FileResourceDTO to FileResourceEntity (para crear)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FileResourceEntity toEntity(FileResourceDTO fileResourceDTO);
}
