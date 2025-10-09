package com.eam.LevelUpCorp.persistenceLayer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long ratingId;
    private LocalDate date = LocalDate.now();

}
