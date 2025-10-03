package com.eam.LevelUpCorp.persistenceLayer.mapper;

import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.LoginDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.ReportEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ReportMapper {

    // De Entity a DTO
    ReportDTO toDTO(ReportEntity entity);

    // De DTO a Entity
    ReportEntity toEntity(ReportDTO dto);

    // Actualizar una entidad existente con datos de un DTO
    void updateEntityFromDTO(ReportDTO dto, @MappingTarget ReportEntity entity);

    // Si necesitas mapear a GeneralReportDTO lo haces con métodos específicos
    GeneralReportDTO toGeneralReportDTO(long totalUsers,
                                        long totalCourses,
                                        long totalRegistrations,
                                        long totalCertificates,
                                        double averageProgress,
                                        double averageScores,
                                        Map<String, Long> usersByRole);


}
