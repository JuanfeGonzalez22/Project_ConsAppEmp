package com.eam.LevelUpCorp.persistenceLayer.mapper;


import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CertificateEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CertificateMapper {


    /*
    Converts a list of CertificateEntity to a list of CertificateDTOs.
     */
    List<CertificateDTO> toDTOList(List<CertificateEntity> certificateEntities);

    /*
    Converts a single CertificateEntity to CertificateDTO.
     */
    CertificateDTO toDTO(CertificateEntity certificateEntity);

    /*
    Converts a CertificateDTO to CertificateEntity.
    Used for creating new certificates in the db.
     */
    @Mapping(target = "id", ignore = true) // id is auto-generated
    CertificateEntity toEntity(CertificateDTO certificateDTO);

    /*
    Updates an existing CertificateEntity using data from a CertificateDTO.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCertificateDTO(CertificateDTO certificateDTO, @MappingTarget CertificateEntity certificateEntity);




}
