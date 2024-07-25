package com.example.fitnessonline.model.entities;

import com.example.fitnessonline.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "moderator")
public class ModeratorEntity {
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
    @Enumerated(EnumType.STRING)
    @Column(name = "role",columnDefinition = "ENUM('ADMIN', 'CONSULTANT')", nullable = false)
    private Role role;
}
