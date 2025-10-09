package com.eam.LevelUpCorp.persistenceLayer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String criterion;

    private String name;

    private String icono;

    private String code;






}
