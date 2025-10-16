package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.LoginDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseInstructorEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CourseInstructorMapper {



    // List of entity to list of DTOs
    List<CourseInstructorResponseDTO> toDTOList(List<CourseInstructorEntity> entities);

    // Entity to ResponseDTO
    @Mapping(target = "courseName", ignore = true)
    @Mapping(target = "instructorName", ignore = true)
    @Mapping(source = "assignedDate", target = "assignedAt")
    CourseInstructorResponseDTO toResponseDTO(CourseInstructorEntity entity);

    // CreateDTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    CourseInstructorEntity toEntity(CourseInstructorDTO dto);

    // Update from CreateDTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    void updateEntityFromDTO(CourseInstructorDTO dto, @MappingTarget CourseInstructorEntity entity);

}
