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
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "courseId", source = "courseId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    ReportDTO toDTO(ReportEntity entity);

    // De DTO a Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "courseId", source = "courseId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    ReportEntity toEntity(ReportDTO dto);

    // Actualizar una entidad existente con datos de un DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "courseId", source = "courseId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    void updateEntityFromDTO(ReportDTO dto, @MappingTarget ReportEntity entity);

    // Mapear a GeneralReportDTO desde parámetros individuales
    @Mapping(target = "totalUsers", source = "totalUsers")
    @Mapping(target = "totalCourses", source = "totalCourses")
    @Mapping(target = "totalRegistrations", source = "totalRegistrations")
    @Mapping(target = "totalCertificates", source = "totalCertificates")
    @Mapping(target = "averageProgress", source = "averageProgress")
    @Mapping(target = "averageScores", source = "averageScores")
    @Mapping(target = "usersByRole", source = "usersByRole")
    GeneralReportDTO toGeneralReportDTO(long totalUsers,
                                        long totalCourses,
                                        long totalRegistrations,
                                        long totalCertificates,
                                        double averageProgress,
                                        double averageScores,
                                        Map<String, Long> usersByRole);

}
