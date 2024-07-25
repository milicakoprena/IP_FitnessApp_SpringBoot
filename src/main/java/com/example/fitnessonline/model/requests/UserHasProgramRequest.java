package com.example.fitnessonline.model.requests;

import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.model.dto.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UserHasProgramRequest {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer programId;
}
