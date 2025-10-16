package com.eam.LevelUpCorp.businessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos que representa un certificado con información del usuario, curso, fecha de emisión y un hash para su verificación.")
public class CertificateResponseDTO {

    @Schema(description = "Identificador único del certificado", example = "1")
    private Long id;

    @Schema(description = "Identificador del usuario que recibió el certificado", example = "123")
    private int userId;

    @Schema(description = "Identificador del curso por el cual se emitió el certificado", example = "456")
    private int courseId;

    @Schema(description = "Fecha en la que se emitió el certificado", example = "2025-10-03")
    private LocalDate issueDate;

    @Schema(description = "Cadena hash única utilizada para verificar la validez del certificado", example = "a1b2c3d4e5f6g7h8i9j0")
    private String hash;
}
