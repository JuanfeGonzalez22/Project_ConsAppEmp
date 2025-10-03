package com.eam.LevelUpCorp.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "course_instructor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstructorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Curso

    @JoinColumn(name = "course_id", nullable = false)
    private Long courseId;

    // Relación con Usuario (Instructor)

    @JoinColumn(name = "user_id", nullable = false)
    private Long instructorId;

    @Column(name = "assigned_date")
    private LocalDate assignedDate = LocalDate.now();



}
