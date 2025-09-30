package com.eam.LevelUpCorp.persistenceLayer.mapper;

import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.LoginDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ReportMapper {


    /*
    Convert a list of ReportEntity to a list of GeneralReportDTO
    */
    @Mapping(source = "name", target = "fullName")
    List<GeneralReportDTO> toDTOList(List<ReportEntity> reportEntities);

    /*
    Convert ReportEntity to GeneralReportDTO
    */
    @Mapping(source = "name", target = "fullName")
    GeneralReportDTO toDTO(ReportEntity reportEntity);



    /*
    Convert GeneralReportDTO to ReportEntity for creating a new user
    */
    @Mapping(source = "fullName", target = "name")
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ReportEntity toEntity(ReportDTO reportDTO);

    /*
    Update an existing ReportEntity using data from GeneralReportDTO
    */
    @Mapping(source = "fullName", target = "name")
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ReportDTO reportDTO, @MappingTarget ReportEntity reportEntity);



}
