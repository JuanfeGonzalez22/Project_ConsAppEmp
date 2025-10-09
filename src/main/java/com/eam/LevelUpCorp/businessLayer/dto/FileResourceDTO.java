package com.eam.LevelUpCorp.businessLayer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResourceDTO {


    private Long id;
    private String fileName;
    private String fileType;
    private Long moduleId;
    private Long evaluationId;
    private Long answerId;
}
