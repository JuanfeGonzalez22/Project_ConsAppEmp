package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.ReportStaticsDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportStaticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ReportsStaticsMapper {


    ReportStaticsDTO toDTO(ReportStaticsEntity entity);

    ReportStaticsEntity toEntity(ReportStaticsDTO dto);

    void updateEntityFromDTO(ReportStaticsDTO dto, @MappingTarget ReportStaticsEntity entity);

}
