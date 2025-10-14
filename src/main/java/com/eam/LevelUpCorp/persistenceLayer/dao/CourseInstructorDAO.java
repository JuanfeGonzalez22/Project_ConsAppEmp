package com.eam.LevelUpCorp.persistenceLayer.dao;


import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseInstructorResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseInstructorEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.CourseInstructorMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseInstructorRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseRepository;
import com.eam.LevelUpCorp.persistenceLayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseInstructorDAO {


    private final CourseInstructorRepository courseInstructorRepository;
    private final CourseInstructorMapper courseInstructorMapper;
    private final CourseRepository  courseRepository;
    private final UserRepository userRepository;

    // Save
    public CourseInstructorResponseDTO save(CourseInstructorDTO requestDTO) {
        CourseInstructorEntity entity = courseInstructorMapper.toEntity(requestDTO);
        CourseInstructorEntity savedEntity = courseInstructorRepository.save(entity);
        return courseInstructorMapper.toResponseDTO(savedEntity);
    }

    // Find by ID
    public Optional<CourseInstructorResponseDTO> findById(Long id) {
        return courseInstructorRepository.findById(id)
                .map(entity -> {
                    // Mapear a DTO primero
                    CourseInstructorResponseDTO dto = courseInstructorMapper.toResponseDTO(entity);

                    // Cargar courseTitle
                    courseRepository.findById(entity.getCourseId())
                            .ifPresent(course -> dto.setCourseName(course.getTitle()));

                    // Cargar instructorName
                    userRepository.findById(entity.getInstructorId())
                            .ifPresent(user -> dto.setInstructorName(user.getName()));

                    return dto;
                });
    }


    //Update.
    public Optional<CourseInstructorResponseDTO> update(Long id, CourseInstructorDTO createDTO) {
        return courseInstructorRepository.findById(id)
                .map(existingEntity -> {
                    // actualizar datos desde el DTO
                    courseInstructorMapper.updateEntityFromDTO(createDTO, existingEntity);
                    CourseInstructorEntity updatedEntity = courseInstructorRepository.save(existingEntity);
                    return courseInstructorMapper.toResponseDTO(updatedEntity);
                });
    }

    // Delete
    public boolean deleteById(Long id) {
        if (courseInstructorRepository.existsById(id)) {
            courseInstructorRepository.deleteById(id);
            return true;
        }
        return false;
    }


    //All CourseInstructor Assignments
    public List<CourseInstructorResponseDTO> findAll() {
        List<CourseInstructorEntity> entities = courseInstructorRepository.findAll();
        return entities.stream()
                .map(entity -> {
                    CourseInstructorResponseDTO dto = courseInstructorMapper.toResponseDTO(entity);

                    // Cargar courseTitle
                    courseRepository.findById(entity.getCourseId())
                            .ifPresent(course -> dto.setCourseName(course.getTitle()));

                    // Cargar instructorName
                    userRepository.findById(entity.getInstructorId())
                            .ifPresent(user -> dto.setInstructorName(user.getName()));

                    return dto;
                })
                .toList();
    }





}
