package com.example.fitnessonline.model.entities;

import com.example.fitnessonline.model.enums.DifficultyLevel;
import com.example.fitnessonline.model.enums.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "program")
public class ProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "title", nullable = false, length = 45)
    private String title;
    @Basic
    @Column(name = "description", nullable = false, length = 300)
    private String description;
    @Basic
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", columnDefinition = "ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED')", nullable = false)
    private DifficultyLevel difficultyLevel;
    @Basic
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "location", columnDefinition = "ENUM('ONLINE', 'GYM', 'PARK')", nullable = false)
    private Location location;
    @Basic
    @Column(name = "instructor_name", nullable = false, length = 45)
    private String instructorName;
    @Basic
    @Column(name = "instructor_contact", nullable = false, length = 45)
    private String instructorContact;
    @Basic
    @Column(name = "video_url", nullable = false, length = 300)
    private String videoUrl;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<ImageEntity> images;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<CommentEntity> comments;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "user_creator_id", referencedColumnName = "id", nullable = false)
    private UserEntity userCreator;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<UserHasProgramEntity> userHasPrograms;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<ProgramHasAttributeEntity> programHasAttributes;

}
