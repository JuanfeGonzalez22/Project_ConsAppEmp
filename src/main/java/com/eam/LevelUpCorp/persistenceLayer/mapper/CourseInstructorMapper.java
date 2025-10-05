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



    // Lista de entidades → lista de DTOs
    List<CourseInstructorResponseDTO> toDTOList(List<CourseInstructorEntity> entities);

    // Entidad → ResponseDTO
    @Mapping(target = "courseName", ignore = true)
    @Mapping(target = "instructorName", ignore = true)
    @Mapping(source = "assignedDate", target = "assignedAt")
    CourseInstructorResponseDTO toResponseDTO(CourseInstructorEntity entity);

    // CreateDTO → Entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    CourseInstructorEntity toEntity(CourseInstructorDTO dto);

    // Update desde CreateDTO → Entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    void updateEntityFromDTO(CourseInstructorDTO dto, @MappingTarget CourseInstructorEntity entity);

}
