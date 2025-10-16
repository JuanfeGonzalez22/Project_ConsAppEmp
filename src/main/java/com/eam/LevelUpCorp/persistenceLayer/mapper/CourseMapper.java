package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseResponseDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CourseMapper {

    /*
    Converts a List of CourseEntity to a list of CourseDTO.
     */
    List<CourseResponseDTO> toDTOList(List<CourseEntity> courseEntities);

    /*
    Converts a single CourseEntity to CourseDTO
     */
    CourseResponseDTO toDTO(CourseEntity courseEntity);

    /*
    Converts a CourseDTO to CourseEntity
     */
    @Mapping(target = "id", ignore = true)
    CourseEntity toEntity(CourseDTO courseDTO);

    /*
    Updates an existing CourseEntity using data from a CourseDTO.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CourseDTO courseDTO, @MappingTarget CourseEntity courseEntity);

}
