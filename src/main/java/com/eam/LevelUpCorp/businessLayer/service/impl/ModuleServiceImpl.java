package com.eam.LevelUpCorp.businessLayer.service.impl;

import com.eam.LevelUpCorp.businessLayer.dto.ModuleDTO;
import com.eam.LevelUpCorp.businessLayer.service.ModuleService;
import com.eam.LevelUpCorp.businessLayer.validate.ModuleValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.ModuleDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class ModuleServiceImpl implements ModuleService {

    private final ModuleDAO moduleDAO;
    private final ModuleValidate moduleValidate;

    /*
    Method create a module.
     */
    @Override
    public ModuleDTO createModule(ModuleDTO moduleDTO){
        log.info("createModule create new Module : {}", moduleDTO);
        moduleValidate.validateCreate(moduleDTO);
        ModuleDTO createModuleDTO = moduleDAO.save(moduleDTO);
        log.info("createModule creates Module : {}", createModuleDTO);
        return createModuleDTO;
    }

    /*
    Method for search a modules.
     */
    @Override
    public ModuleDTO getModule(Long id){
        log.info("Get module by ID : {}", id);
        moduleValidate.validateSearch(id);
        return moduleDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Módulo no encontrado con ID: " + id));
    }

    /*
    Method to obtain all modules.
     */
    @Override
    public List<ModuleDTO> getModules(){
        log.info("Get modules by module");
        List<ModuleDTO> modules = moduleDAO.findAll();
        if(modules.isEmpty()){
            log.warn("No hay módulos disponibles");
            throw new RuntimeException("No hay módulos disponibles");
        }
        log.info("Found {} modules", modules.size());
        return modules;
    }

    /*
    Method delete a modules.
     */
    @Override
    public void deleteModule(Long id){
        log.info("Delete module by ID: {}", id);
        getModule(id);
        moduleValidate.validateDelete(id);

        boolean deleted = moduleDAO.deleteById(id);
        if(!deleted){
            throw new RuntimeException("Error al eliminar el módulo con ID: " + id);
        }
        log.info("Module successfully deleted ID: {}", id);
    }
    /*
    Method update a module.
     */
    @Override
    public ModuleDTO updateModule(Long id, ModuleDTO moduleDTO){
        log.info("Update module by ID: {}", id);
        getModule(id);
        moduleValidate.validateUpdate(id, moduleDTO);
        ModuleDTO updateModule = moduleDAO.update(id, moduleDTO).orElseThrow(() -> new RuntimeException("Error al actualizar módulo con ID: " + id));
        log.info("Module updated successfully ID: {}", id);
        return updateModule;
    }

}
