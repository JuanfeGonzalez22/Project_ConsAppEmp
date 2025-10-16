package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para crear un informe con detalles como usuario, tipo, descripción, fecha, formato y contenido.")
public class CreateReportDTO {

    @Schema(description = "ID del usuario que está creando el informe", example = "101")
    private int userId;

    @Schema(description = "Tipo de informe", example = "Evaluación")
    private String type;

    @Schema(description = "Descripción breve del informe", example = "Evaluación mensual del desempeño del equipo de ventas")
    private String description;

    @Schema(description = "Formato del informe", example = "PDF")
    private String format;

    @Schema(description = "Contenido principal del informe", example = "Este es el texto completo o los datos estructurados del informe")
    private String content;
}
