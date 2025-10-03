package com.eam.LevelUpCorp.businessLayer.service.impl;

import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorResponseDTO;
import com.eam.LevelUpCorp.businessLayer.service.CourseInstructorService;
import com.eam.LevelUpCorp.businessLayer.validate.CourseInstructorValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.CourseInstructorDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CourseInstructorServiceImpl implements CourseInstructorService {

    private final CourseInstructorDAO courseInstructorDAO;
    private final CourseInstructorValidate courseInstructorValidate;

    /*
     * Crear nueva asignación de instructor a curso
     */
    @Override
    public CourseInstructorResponseDTO createAssignment(CourseInstructorDTO createDTO) {
        log.info("Creating new course-instructor assignment: CourseId={}, InstructorId={}", createDTO.getCourseId(), createDTO.getInstructorId());
        courseInstructorValidate.validateCreate(createDTO);
        CourseInstructorResponseDTO created = courseInstructorDAO.save(createDTO);
        log.info("Assignment created successfully with ID: {}", created.getId());
        return created;
    }

    /*
     * Buscar asignación por ID
     */
    @Override
    @Transactional(readOnly = true)
    public CourseInstructorResponseDTO getAssignmentById(Long id) {
        log.info("Searching CourseInstructor by ID: {}", id);
        courseInstructorValidate.validateSearch(id);
        return courseInstructorDAO.findById(id).orElseThrow(() -> {
            log.warn("CourseInstructor not found with ID: {}", id);
            return new RuntimeException("Assignment not found with ID: " + id);
        });
    }

    /*
     * Listar todas las asignaciones
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourseInstructorResponseDTO> getAllAssignments() {
        log.info("Getting all course-instructor assignments");
        List<CourseInstructorResponseDTO> assignments = courseInstructorDAO.findAll();

        if (assignments.isEmpty()) {
            log.warn("No assignments found");
            throw new RuntimeException("No course-instructor assignments available");
        }

        log.info("Found {} assignments", assignments.size());
        return assignments;
    }

    /*
     * Actualizar asignación
     */
    @Override
    public CourseInstructorResponseDTO updateAssignment(Long id, CourseInstructorDTO createDTO) {
        log.info("Updating assignment with ID: {}", id);
        getAssignmentById(id); // valida que exista antes de actualizar
        courseInstructorValidate.validateUpdate(id,  createDTO);
        CourseInstructorResponseDTO updated = courseInstructorDAO.update(id, createDTO)
                .orElseThrow(() -> new RuntimeException("Error updating assignment"));
        log.info("Assignment updated successfully ID: {}", id);
        return updated;
    }

    /*
     * Delete
     */
    @Override
    public void deleteAssignment(Long id) {
        log.info("Deleting assignment with ID: {}", id);
        getAssignmentById(id); // valida que exista
        courseInstructorValidate.validateDelete(id);
        boolean deleted = courseInstructorDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error deleting assignment with ID: " + id);
        }
        log.info("Assignment deleted successfully ID: {}", id);
    }


}
