package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import org.springframework.stereotype.Component;

@Component
public class ReportValidate {

    public void validateCreate(ReportDTO reportDTO) {
        if (reportDTO == null) {
            throw new IllegalArgumentException("The report is null");
        }
        if (reportDTO.getUserId() == null || reportDTO.getUserId() <= 0) {
            throw new IllegalArgumentException("The userId must be provided and greater than 0");
        }
        if (reportDTO.getCourseId() == null || reportDTO.getCourseId() <= 0) {
            throw new IllegalArgumentException("The courseId must be provided and greater than 0");
        }
        if (reportDTO.getTitle() == null || reportDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("The title is required");
        }
        if (reportDTO.getTitle().length() > 150) {
            throw new IllegalArgumentException("The title cannot exceed 150 characters");
        }
        if (reportDTO.getDescription() == null || reportDTO.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("The description is required");
        }
        if (reportDTO.getDescription().length() > 1000) {
            throw new IllegalArgumentException("The description cannot exceed 1000 characters");
        }
    }

    public void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid report ID");
        }
    }

    public void validateUpdate(Long id, ReportDTO reportDTO) {
        validateSearch(id);
        validateCreate(reportDTO);
    }

    public void validateDelete(Long id) {
        validateSearch(id);
    }
}
