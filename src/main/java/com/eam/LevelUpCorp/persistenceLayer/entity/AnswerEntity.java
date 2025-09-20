package com.eam.LevelUpCorp.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "Answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int evaluationId;
    private int userId;
    private double score;
    private LocalDate date;


}
