package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un módulo dentro de un curso")
public class ModuleDTO {


    private int cursoId;
    private String titulo;
    private String tipo; //video, texto, quiz y practica
    private int orden;

}
