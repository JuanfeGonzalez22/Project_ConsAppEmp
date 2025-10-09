package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.AnswerResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.GradeAnswerDTO;
import com.eam.LevelUpCorp.businessLayer.dto.SubmitAnswerDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.AnswerEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AnswerMapper {

    //Convert AnswerEntity to AnswerResponseDTO
    AnswerResponseDTO toDTO(AnswerEntity answerEntity);


    //Convert List of AnswerEntity a list of AnswerResponseDTO
    List<AnswerResponseDTO> toDTO(List<AnswerEntity> answerEntityList);


    //Convert SubmitAnswerDTO to AnswerEntity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true) // Se setea en el servicio
    @Mapping(target = "score", ignore = true)
    @Mapping(target = "answerFileId", ignore = true) // Se setea después de subir archivo
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnswerEntity toEntity(SubmitAnswerDTO submitAnswerDTO);


    //Update AnswerEntity for rating
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "answerFileId", ignore = true)
    @Mapping(target = "date", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromGradeDTO(GradeAnswerDTO gradeAnswerDTO, @MappingTarget AnswerEntity answerEntity);



}
