package com.eam.LevelUpCorp.persistenceLayer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FileResource")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private Long moduleId;
    private Long evaluationId;


}
