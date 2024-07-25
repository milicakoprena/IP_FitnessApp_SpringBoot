package com.example.fitnessonline.model.entities;

import com.example.fitnessonline.model.enums.ExerciseType;
import com.example.fitnessonline.model.enums.Intensity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Table(name = "fitness_tracker")
public class FitnessTrackerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_type", columnDefinition = "ENUM('CARDIO', 'STRENGTH', 'HIIT')", nullable = false)
    private ExerciseType exerciseType;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "intensity", columnDefinition = "ENUM('LOW', 'MODERATE', 'HIGH', 'INTENSE')", nullable = false)
    private Intensity intensity;
    @Basic
    @Column(name = "weight", nullable = false)
    private BigDecimal weight;
    @Basic
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
