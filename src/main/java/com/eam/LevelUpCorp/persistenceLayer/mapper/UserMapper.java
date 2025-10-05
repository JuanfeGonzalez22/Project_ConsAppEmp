package com.eam.LevelUpCorp.persistenceLayer.mapper;
import com.eam.LevelUpCorp.businessLayer.dto.LoginDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserRegisterDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserMapper {

    /*
    Convert a list of UserEntity to a list of UserDTO
    */
    @Mapping(source = "name", target = "fullName") // map name -> fullName
    List<UserDTO> toDTOList(List<UserEntity> userEntities);

    /*
    Convert UserEntity to UserDTO
    */
    @Mapping(source = "name", target = "fullName") // map name -> fullName
    UserDTO toDTO(UserEntity userEntity);

    /*
    Convert LoginDTO to UserEntity
    */
    UserEntity toEntity(LoginDTO loginDTO);

    /*
     * Convert UserRegisterDTO to UserEntity for registration
     */
    @Mapping(target = "id", ignore = true)       
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity toEntity(UserRegisterDTO registerDTO);

    /*
    Convert UserEntity to LoginDTO
    */
    LoginDTO toLoginDTO(UserEntity userEntity);

    /*
    Convert UserDTO to UserEntity for creating a new user
    */
    @Mapping(source = "fullName", target = "name") // map fullName -> name
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity toEntity(UserDTO userDTO);

    /*
    Update an existing UserEntity using data from UserDTO
    */
    @Mapping(source = "fullName", target = "name") // map fullName -> name
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UserDTO userDTO, @MappingTarget UserEntity userEntity);

}
