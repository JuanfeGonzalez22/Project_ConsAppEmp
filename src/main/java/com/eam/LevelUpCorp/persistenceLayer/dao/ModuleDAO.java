package com.eam.LevelUpCorp.persistenceLayer.dao;

import com.eam.LevelUpCorp.businessLayer.dto.CourseDTO;
import com.eam.LevelUpCorp.businessLayer.dto.ModuleDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.persistenceLayer.entity.CourseEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.ModuleEntity;
import com.eam.LevelUpCorp.persistenceLayer.entity.UserEntity;
import com.eam.LevelUpCorp.persistenceLayer.mapper.ModuleMapper;
import com.eam.LevelUpCorp.persistenceLayer.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor

public class ModuleDAO {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    //Save
    public ModuleDTO save(ModuleDTO moduleDTO){
        ModuleEntity moduleEntity = moduleMapper.toEntity(moduleDTO);
        ModuleEntity savedModuleEntity1 = moduleRepository.save(moduleEntity);
        return moduleMapper.toDTO(savedModuleEntity1);
    }

    //Search
    public Optional<ModuleDTO> findById(Long id){

        return moduleRepository.findById(id).map(moduleMapper::toDTO);
    }

    //Update
    public Optional<ModuleDTO> update(Long id, ModuleDTO moduleDTO) {
        return moduleRepository.findById(id)
                .map(existingEntity -> {
                    moduleMapper.updateEntity(moduleDTO, existingEntity);
                    ModuleEntity updatedEntity = moduleRepository.save(existingEntity);
                    return moduleMapper.toDTO(updatedEntity);
                });
    }


    //delete
    public boolean deleteById(Long id){
        if(moduleRepository.existsById(id)){
            moduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //All modules
    public List<ModuleDTO> findAll() {
        return moduleRepository.findAll().stream().map(moduleMapper::toDTO).toList();
    }
}
