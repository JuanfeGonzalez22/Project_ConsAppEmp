package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseResponseDTO;

import java.util.List;

public interface CourseService {

    //Create.
    CourseResponseDTO createCourse(CourseDTO courseDTO);

    //Search for ID.
    CourseResponseDTO getCourse(Long id);

    //Get everything all the courses.
    List<CourseResponseDTO> getCourses();

    //Delete for ID.
    void deleteCourse(Long id);

    //Update.
    CourseResponseDTO updateCourse(Long id, CourseDTO courseDTO);

}
