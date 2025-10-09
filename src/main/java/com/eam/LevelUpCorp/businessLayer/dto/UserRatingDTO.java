package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents user rating information in the platform")
public class UserRatingDTO {


    @Schema(description = "Unique identifier of the user being rated", example = "123")
    private Long userId;

    @Schema(description = "Unique identifier of the rating assigned to the user", example = "456")
    private Long ratingId;

}
