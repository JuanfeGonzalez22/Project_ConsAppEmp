package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "This DTO represents a badge or reward within the platform")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDTO {

    @Schema(description = "ID del rating", example = "1")
    private Long id;

    @Schema(description = "Name of the reward", example = "Top performer")
    private String name;

    @Schema(description = "Condition required to obtain the reward", example = "Complete 3 courses")
    private String criterion;

    @Schema(description = "Path or filename of the badge icon", example = "medal.png")
    private String icon;

    private String code;
}
