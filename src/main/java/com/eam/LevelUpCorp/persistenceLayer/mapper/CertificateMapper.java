package com.eam.LevelUpCorp.persistenceLayer.mapper;

import com.eam.LevelUpCorp.businessLayer.dto.CertificateDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CertificateResponseDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CertificateEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CertificateMapper {

    /*
     * Converts a list of CertificateEntity to a list of CertificateDTOs.
     */
    @Mapping(source = "emissionDate", target = "issueDate")
    List<CertificateResponseDTO> toDTOList(List<CertificateEntity> certificateEntities);

    /*
     * Converts a single CertificateEntity to CertificateDTO.
     */
    @Mapping(source = "emissionDate", target = "issueDate")
    CertificateResponseDTO toDTO(CertificateEntity certificateEntity);

    /*
     * Converts a CertificateDTO to CertificateEntity.
     */
    @Mapping(target = "id", ignore = true) // id es autogenerado
    @Mapping(source = "issueDate", target = "emissionDate") // mapea issueDate -> emissionDate
    CertificateEntity toEntity(CertificateDTO certificateDTO);

    /*
     * Updates an existing CertificateEntity using data from a CertificateDTO.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "issueDate", target = "emissionDate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CertificateDTO certificateDTO, @MappingTarget CertificateEntity certificateEntity);

}
