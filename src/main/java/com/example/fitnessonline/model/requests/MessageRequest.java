package com.example.fitnessonline.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {
    @NotBlank
    private String content;
    @NotNull
    private Integer userId;
}
