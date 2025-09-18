package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Este es un objeto de datos que muestra un certificado con usuario, curso, fecha de emisión y un hash para verificar.")
public class CertificateDTO {

    @Schema(description = "ID del usuario que recibió el certificado", example = "123")
    private int usuarioId;

    @Schema(description = "ID del curso para el que es el certificado", example = "456")
    private int cursoId;

    @Schema(description = "La fecha en que se emitió el certificado", example = "2024-01-15")
    private LocalDate fechaEmision;

    @Schema(description = "Un hash único que representa el certificado para verificarlo", example = "a1b2c3d4e5f6g7h8i9j0")
    private String hash;

}
