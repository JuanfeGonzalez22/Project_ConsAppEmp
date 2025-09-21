package com.eam.LevelUpCorp.persistenceLayer.mapper;
import com.eam.LevelUpCorp.businessLayer.dto.LoginDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserMapper {


    /*
    Converts to list of UserEntity to list of UserDTO.
     */
    List<UserDTO> toDTOList(List<UserEntity> userEntities);


    /*
    Converts UserEntity to UserDTO.
     */
    UserDTO toDTO(UserEntity userEntity);

    /*
    Converts to LoginDTO to UserEntity.
     */
    UserEntity toEntity(LoginDTO loginDTO);

    /*
    Converts to UserEntity to LoginDTO
     */
    LoginDTO toLoginDTO(UserEntity userEntity);


    /*
      Converts a UserDTO to a UserEntity.
      Used for creating new users in the database.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity toEntity(UserDTO userDTO);


    /*
     Updates an existing UserEntity using data from a UserDTO.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UserDTO userDTO, @MappingTarget UserEntity userEntity);



}
