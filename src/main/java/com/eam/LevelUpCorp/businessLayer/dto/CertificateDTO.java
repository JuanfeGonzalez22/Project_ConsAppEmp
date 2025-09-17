package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Este es un objeto de datos que muestra un certificado con usuario, curso, fecha de emisión y un hash para verificar.")
public class CertificateDTO {

    private int usuarioId;
    private int cursoId;
    private LocalDate fechaEmision;
    private String hash;

}
