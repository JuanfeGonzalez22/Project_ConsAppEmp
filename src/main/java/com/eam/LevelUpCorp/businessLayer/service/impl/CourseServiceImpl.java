package com.eam.LevelUpCorp.businessLayer.service.impl;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.CourseService;
import com.eam.LevelUpCorp.businessLayer.validate.CourseValidate;
import com.eam.LevelUpCorp.businessLayer.validate.UserValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.CourseDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO;
    private  final CourseValidate courseValidate;



    /*
    Method create a course.
     */
    @Override
    public CourseResponseDTO createCourse(CourseDTO courseDTO) {
        log.info("createCourse new Course : {}", courseDTO);
        courseValidate.validateCreate(courseDTO);
        CourseResponseDTO createCourseDTO = courseDAO.save(courseDTO);
        log.info("createCourse create Course : {}", createCourseDTO);

        return createCourseDTO;
    }



    /*
    Method for search a course.
     */
    @Override
    public CourseResponseDTO getCourse(Long id) {
        log.info("Get course by ID: {}", id);
        courseValidate.validateSearch(id);
        return courseDAO.findById(id).orElseThrow(() -> {
            log.warn("Get course by ID failure: {}", id);
            return new RuntimeException("Course not found whit ID:" + id);
        });
    }


    /*
    Method to obtain all courses.
     */
    @Override
    public List<CourseResponseDTO> getCourses() {
        log.info("Get courses by course");
        List<CourseResponseDTO> courses =  courseDAO.findAll();
        if (courses.isEmpty()) {
            log.warn("No course found");
            throw new RuntimeException("No courses available");
        }
        log.info("Found {} courses", courses.size());
        return courses;
    }



    /*
    Method delete a course.
     */
    @Override
    public void deleteCourse(Long id) {

        log.info("Delete course by ID: {}", id);
        getCourse(id); // asegura que exista
        courseValidate.validateDelete(id);

        boolean deleted = courseDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar el curso con ID: " + id);
        }
        log.info("Course successfully deleted ID: {}", id);

    }



    /*
    Method update a course.
     */
    @Override
    public CourseResponseDTO updateCourse(Long id, CourseDTO courseDTO) {

        log.info("Update course by ID: {}", id);
        getCourse(id);

        courseValidate.validateUpdate(id, courseDTO);
        CourseResponseDTO updatedCourse = courseDAO.update(id, courseDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar curso con ID: " + id));

        log.info("Course updated successfully ID: {}", id);
        return updatedCourse;

    }
}
