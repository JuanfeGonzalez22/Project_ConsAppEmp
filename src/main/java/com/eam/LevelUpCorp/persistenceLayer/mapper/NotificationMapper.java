package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.NotificationDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.NotificationEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface NotificationMapper {


    NotificationDTO toDTO(NotificationEntity notificationEntity);

    List<NotificationDTO> toDTOList(List<NotificationEntity> notificationEntities);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentDate", expression = "java(java.time.LocalDate.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NotificationEntity toEntity(NotificationDTO notificationDTO);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(NotificationEntity notificationEntity, @MappingTarget NotificationDTO notificationDTO);

}
