package com.example.fitnessonline.model.dto;

import com.example.fitnessonline.model.entities.*;
import com.example.fitnessonline.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String city;
    private String avatarUrl;
    private String mail;
    private UserStatus status;
}
