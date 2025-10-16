package com.eam.LevelUpCorp.businessLayer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un recurso de archivo subido (asociado a un módulo o una evaluación)")
public class FileResourceDTO {

    @Schema(description = "Identificador único del recurso de archivo", example = "1")
    private Long id;

    @Schema(description = "Nombre del archivo subido", example = "document.pdf")
    private String fileName;

    @Schema(description = "Tipo MIME del archivo", example = "application/pdf")
    private String fileType;

    @Schema(description = "ID del módulo al que pertenece este archivo", example = "10")
    private Long moduleId;

    @Schema(description = "ID de la evaluación a la que pertenece este archivo", example = "25")
    private Long evaluationId;

    @Schema(description = "URL al archivo de la respuesta si fue enviado como archivo", example = "https://storage.com/answers/answer-123.pdf")
    private String fileUrl;
}
