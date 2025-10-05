package com.eam.LevelUpCorp.businessLayer.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object that represents a certificate with user, course, issue date, and a hash for verification.")
public class CertificateDTO {

    @Schema(description = "ID of the user who received the certificate", example = "123")
    private int userId;

    @Schema(description = "ID of the course for which the certificate was issued", example = "456")
    private int courseId;

    @Schema(description = "Date when the certificate was issued", example = "2025-10-03")
    private LocalDate issueDate;

    @Schema(description = "Unique hash string to verify the certificate", example = "a1b2c3d4e5f6g7h8i9j0")
    private String hash;

}
