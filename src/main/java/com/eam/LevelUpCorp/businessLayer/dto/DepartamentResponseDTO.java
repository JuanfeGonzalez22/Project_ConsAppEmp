package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to represent department information returned in responses, including its ID, name, and description.")
public class DepartamentResponseDTO {
    @Schema(description = "Unique identifier of the department", example = "1001")
    private int id;
    @Schema(description = "Name of the department", example = "Human Resources")
    private String name;
    @Schema(description = "Detailed explanation of the department's purpose or role in the company", example = "Handles employee relations and company policies")
    private String description;
}
