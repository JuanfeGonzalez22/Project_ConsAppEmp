package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.EvaluationDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.EvaluationEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface EvaluationMapper {

    /*
    Converts a list of EvaluationEntity to a list of EvaluationDTOs.
     */
    List<EvaluationDTO> toDTOList(List<EvaluationEntity> evaluationEntities);

    /*
    Converts a single EvaluationEntity to EvaluationDTO.
     */
    EvaluationDTO toDTO(EvaluationEntity evaluationEntity);

    /*
    Converts a EvaluationDTO to EvaluationEntity.
     */
    @Mapping(target = "id", ignore = true) // id is auto-generated
    EvaluationEntity toEntity(EvaluationDTO evaluationDTO);

    /*
    Updates an existing EvaluationEntity using data from an EvaluationDTO.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(EvaluationDTO evaluationDTO, @MappingTarget EvaluationEntity evaluationEntity);


}
