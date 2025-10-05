package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorResponseDTO;

import java.util.List;

public interface CourseInstructorService {


    //Create
    CourseInstructorResponseDTO createAssignment(CourseInstructorDTO createDTO);

    //Get by ID
    CourseInstructorResponseDTO getAssignmentById(Long id);

    //Get all
    List<CourseInstructorResponseDTO> getAllAssignments();

    //Update
    CourseInstructorResponseDTO updateAssignment(Long id, CourseInstructorDTO createDTO);

    //Delete
    void deleteAssignment(Long id);
}
