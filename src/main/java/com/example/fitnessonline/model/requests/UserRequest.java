package com.example.fitnessonline.model.requests;

import com.example.fitnessonline.model.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String city;
    private String avatarUrl;
    @NotBlank
    private String mail;
}
