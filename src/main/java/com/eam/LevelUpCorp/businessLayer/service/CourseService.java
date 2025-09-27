package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    //Create.
    CourseDTO createCourse(CourseDTO courseDTO);

    //Search for ID.
    CourseDTO getCourse(Long id);

    //Get everything all the courses.
    List<CourseDTO> getCourses();

    //Delete for ID.
    void deleteCourse(Long id);

    //Update.
    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

}
