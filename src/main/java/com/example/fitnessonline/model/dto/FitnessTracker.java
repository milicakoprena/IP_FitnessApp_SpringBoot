package com.example.fitnessonline.model.dto;

import com.example.fitnessonline.model.enums.ExerciseType;
import com.example.fitnessonline.model.enums.Intensity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessTracker implements Serializable {
    private Integer id;
    private ExerciseType exerciseType;
    private Intensity intensity;
    private BigDecimal weight;
    private Integer duration;
    private Date date;
    private User user;
}
