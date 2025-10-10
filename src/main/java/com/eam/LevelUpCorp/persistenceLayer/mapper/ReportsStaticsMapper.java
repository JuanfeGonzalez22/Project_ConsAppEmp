package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.ReportStaticsDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportStaticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ReportsStaticsMapper {


    // Convert Entity to DTO
    @Mapping(target = "reportId", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "usersByRole", source = "usersByRole")
    ReportStaticsDTO toDTO(ReportStaticsEntity entity);

    // Convert DTO to Entity
    @Mapping(target = "id", source = "reportId")
    @Mapping(target = "createdAt", expression = "java(dto.getCreatedAt() != null ? dto.getCreatedAt() : java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "usersByRole", source = "usersByRole")
    ReportStaticsEntity toEntity(ReportStaticsDTO dto);

    // Update Entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "usersByRole", source = "usersByRole")
    void updateEntityFromDTO(ReportStaticsDTO dto, @MappingTarget ReportStaticsEntity entity);
}
