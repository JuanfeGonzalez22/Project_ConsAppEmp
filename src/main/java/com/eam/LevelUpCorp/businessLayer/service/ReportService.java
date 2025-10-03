package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.GeneralReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.InstructorReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ReportDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;

import java.util.List;

public interface ReportService {



    //Create.
    GeneralReportDTO createReport(ReportDTO reportDTO);

    //Search for ID.
    GeneralReportDTO getReportById(Long id);

    //Search for everyone.
    List<GeneralReportDTO> getAllReports();

    //Delete.
    void deleteReport(Long id);

    //Update.
    GeneralReportDTO updateReport(Long id, ReportDTO reportDTO);

    InstructorReportDTO generateInstructorReport(Long instructorId);

}
