package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents a module inside a course")
public class ModuleDTO {


    @Schema(description = "ID of the course this module belongs to", example = "1001")
    private int courseId;

    @Schema(description = "Name or title of the module", example = "Introduction to the platform")
    private String title;

    @Schema(description = "Type of module (video, text, quiz, or practice)", example = "video")
    private String type; //video, text, quiz and practice

    @Schema(description = "Order in which the module appears inside the course", example = "1")
    private int order;

}
