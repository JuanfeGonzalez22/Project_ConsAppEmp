package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.UserRatingDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserRatingResponseDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserRatingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserRatingMapper {


    // Convert Entity to ResponseDTO
    UserRatingResponseDTO toResponseDTO(UserRatingEntity entity);

    // Convert List<Entity> to List<ResponseDTO>
    List<UserRatingResponseDTO> toResponseDTOList(List<UserRatingEntity> entities);

    // Convert CreateDTO to Entity (para crear)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    UserRatingEntity toEntity(UserRatingDTO createDTO);


}
