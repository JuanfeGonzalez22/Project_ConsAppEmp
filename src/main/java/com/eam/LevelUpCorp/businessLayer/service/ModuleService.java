package com.eam.LevelUpCorp.businessLayer.service;
import com.eam.LevelUpCorp.businessLayer.dto.ModuleDTO;

import java.util.List;

public interface ModuleService {

    //Create
    ModuleDTO createModule(ModuleDTO moduleDTO);

    //Search for ID
    ModuleDTO getModule(Long id);

    //Get everything all the modules.
    List<ModuleDTO> getModules();

    //Delete for ID.
    void deleteModule(Long id);

    //Update.
    ModuleDTO updateModule(Long id, ModuleDTO moduleDTO);
}
