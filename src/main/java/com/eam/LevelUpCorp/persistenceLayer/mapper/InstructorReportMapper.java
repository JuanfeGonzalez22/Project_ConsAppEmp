package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.InstructorReportDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface InstructorReportMapper {


    // Entity to DTO
    @Mapping(target = "instructorId", ignore = true)
    @Mapping(target = "instructorName", ignore = true)
    @Mapping(target = "assignedCourses", ignore = true)
    InstructorReportDTO toDTO(ReportEntity entity);

    // DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "courseId", ignore = true)
    @Mapping(target = "title", ignore = true) 
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    ReportEntity toEntity(InstructorReportDTO dto);

    // List<Entity> to List<DTO>
    List<InstructorReportDTO> toDTOList(List<ReportEntity> entities);
}
