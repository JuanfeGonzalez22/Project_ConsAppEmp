package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.InstructorReportDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface InstructorReportMapper {


    // Entity -> DTO
    InstructorReportDTO toDTO(ReportEntity entity);

    // DTO -> Entity
    ReportEntity toEntity(InstructorReportDTO dto);

    // List<Entity> -> List<DTO>
    List<InstructorReportDTO> toDTOList(List<ReportEntity> entities);
}
