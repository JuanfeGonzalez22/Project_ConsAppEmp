package com.eam.LevelUpCorp.persistenceLayer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_resource")
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
    @Column(name = "file_url")
    private String fileUrl;




}
