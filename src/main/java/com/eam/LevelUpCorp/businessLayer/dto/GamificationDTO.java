package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Schema(description = "This DTO represents a badge or reward within the platform")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamificationDTO {
    @Schema(description = "Identifier of the badge or reward", example = "1")
    private Long id;

    @Schema(description = "Name of the reward", example = "Top performer")
    private String name;

    @Schema(description = "Condition required to obtain the reward", example = "Complete 3 courses")
    private String criteria;

    @Schema(description = "Path or filename of the badge icon", example = "medal.png")
    private String icon;
}
