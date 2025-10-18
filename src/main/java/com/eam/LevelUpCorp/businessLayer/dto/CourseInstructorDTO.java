package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para asignar un instructor a un curso")
public class CourseInstructorDTO {

    @Schema(description = "Identificador del curso que se va a asignar", example = "5")
    private Long courseId;

    @Schema(description = "Identificador del instructor que se va a asignar", example = "2")
    private Long instructorId;
}
