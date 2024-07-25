package com.example.fitnessonline.model.requests;

import com.example.fitnessonline.model.dto.User;
import com.example.fitnessonline.model.enums.ExerciseType;
import com.example.fitnessonline.model.enums.Intensity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FitnessTrackerRequest {
    @NotNull
    private ExerciseType exerciseType;
    @NotNull
    private Intensity intensity;
    @NotNull
    private BigDecimal weight;
    @NotNull
    private Integer duration;
    @NotNull
    private Integer userId;
}
