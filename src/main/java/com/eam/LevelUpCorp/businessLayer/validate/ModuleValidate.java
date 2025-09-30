package com.eam.LevelUpCorp.businessLayer.validate;
import com.eam.LevelUpCorp.businessLayer.dto.ModuleDTO;
import org.springframework.stereotype.Component;

public class ModuleValidate {

    public void validateCreate(ModuleDTO moduleDTO){
        if(moduleDTO == null){
            throw new IllegalArgumentException("El módulo no puede ser nulo");
        }
        if(moduleDTO.getCourseId() <= 0 ){
            throw new IllegalArgumentException("El cursoId es obligatorio y debe ser válido");
        }
        if(moduleDTO.getTitle() == null || moduleDTO.getTitle().trim().isEmpty()){
            throw new IllegalArgumentException("El título del módulo es obligatorio");
        }
        if(moduleDTO.getType() == null || moduleDTO.getType().trim().isEmpty()){
            throw new IllegalArgumentException("El tipo del módulo es obligatorio");
        }
        if(moduleDTO.getOrder() <= 0){
            throw new IllegalArgumentException("El orden del módulo es obligatorio y debe ser mayor a 0");
        }
    }

    public void validateSearch(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("El id del módulo debe ser válido");
        }
    }

    public void validateUpdate(Long id, ModuleDTO moduleDTO){
        validateSearch(id);
        validateCreate(moduleDTO);
    }

    public  void validateDelete(Long id){
        validateSearch(id);
    }

}
