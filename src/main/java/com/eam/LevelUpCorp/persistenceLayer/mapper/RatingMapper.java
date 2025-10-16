package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.RatingDTO;
import com.eam.LevelUpCorp.businessLayer.dto.RatingResponseDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.RatingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface RatingMapper {


    // Convert Entity to ResponseDTO
    RatingResponseDTO toDTO(RatingEntity entity);

    // Convert List<Entity> to List<ResponseDTO>
    List<RatingResponseDTO> toResponseDTOList(List<RatingEntity> entities);

    // Convert CreateDTO to Entity (para crear)
    @Mapping(target = "id", ignore = true)
    RatingEntity toEntity(RatingDTO createDTO);

    // Update Entity from CreateDTO
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(RatingDTO createDTO, @MappingTarget RatingEntity entity);



}
