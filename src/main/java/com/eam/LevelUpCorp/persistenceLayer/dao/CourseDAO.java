package com.eam.LevelUpCorp.persistenceLayer.dao;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.CourseResponseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.CourseMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CourseDAO {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    //Save
    public CourseResponseDTO save(CourseDTO courseDTO) {
        CourseEntity courseEntity = courseMapper.toEntity(courseDTO);
        CourseEntity savedCourseEntity = courseRepository.save(courseEntity);
        return courseMapper.toDTO(savedCourseEntity);

    }


    //Search
    public Optional<CourseResponseDTO> findById(Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO);

    }


    //Update
    public Optional<CourseResponseDTO> update(Long id, CourseDTO courseDTO) {
        return courseRepository.findById(id)
                .map(existingEntity -> {
                    courseMapper.updateEntity(courseDTO, existingEntity);
                    CourseEntity updatedEntity = courseRepository.save(existingEntity);
                    return courseMapper.toDTO(updatedEntity);
                });
    }


    //Delete
    public boolean deleteById(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //All courses.
    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll()
                .stream().map(courseMapper::toDTO).toList();
    }
}
