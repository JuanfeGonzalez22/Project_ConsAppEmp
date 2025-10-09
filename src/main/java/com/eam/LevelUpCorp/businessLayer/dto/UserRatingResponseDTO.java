package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that represents the basic information of a user in the platform")
public class UserRatingResponseDTO {



    @Schema(description = "Unique identifier of the rating record", example = "1")
    private Long id;


    @Schema(description = "ID of the user being rated", example = "123")
    private Long userId;


    @Schema(description = "ID of the specific rating", example = "456")
    private Long ratingId;


    @Schema(description = "Date when the rating was given", example = "2024-01-15")
    private LocalDate date = LocalDate.now();
}
