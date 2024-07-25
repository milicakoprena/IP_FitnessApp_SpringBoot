package com.example.fitnessonline.model.entities;

import com.example.fitnessonline.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    @Basic
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 500)
    private String password;
    @Basic
    @Column(name = "city", nullable = false, length = 45)
    private String city;
    @Basic
    @Column(name = "avatar_url", length = 1024)
    private String avatarUrl;
    @Basic
    @Column(name = "mail", nullable = false, length = 45)
    private String mail;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('REQUESTED', 'ACTIVE', 'INACTIVE')", nullable = false)
    private UserStatus status;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<FitnessTrackerEntity> fitnessTrackers;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MessageEntity> messages;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserHasCategoryEntity> userHasCategories;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserHasProgramEntity> userHasPrograms;
}
