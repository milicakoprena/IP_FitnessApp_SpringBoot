package com.example.fitnessonline.model.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountActivationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String pin;
}