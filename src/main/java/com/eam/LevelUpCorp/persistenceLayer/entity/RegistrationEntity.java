package com.eam.LevelUpCorp.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "Registration")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int userId;
    private int courseId;
    private double progress;
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    private String status;


}
