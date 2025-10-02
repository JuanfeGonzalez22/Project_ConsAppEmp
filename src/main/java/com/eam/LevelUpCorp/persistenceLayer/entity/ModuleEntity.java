package com.eam.LevelUpCorp.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Module")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private String title;
    @Column(name = "module_type")
    private String type; //video, texto, quiz y practica
    @Column(name = "module_order")
    private int order;


}
