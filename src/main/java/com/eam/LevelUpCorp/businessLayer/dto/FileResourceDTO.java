package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResourceDTO {


    @Schema(description = "Unique identifier of the file resource", example = "1")
    private Long id;

    @Schema(description = "Name of the uploaded file", example = "document.pdf")
    private String fileName;

    @Schema(description = "MIME type of the file", example = "application/pdf")
    private String fileType;

    @Schema(description = "ID of the module this file belongs to", example = "10")
    private Long moduleId;

    @Schema(description = "ID of the evaluation this file belongs to", example = "25")
    private Long evaluationId;

//    @Schema(description = "ID of the answer this file belongs to", example = "50")
//    private Long answerId;

    @Schema(description = "URL to the answer file if submitted as file", example = "https://storage.com/answers/answer-123.pdf")
    private String fileUrl;

}
