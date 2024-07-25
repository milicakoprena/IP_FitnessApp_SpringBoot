package com.example.fitnessonline.model.requests;

import com.example.fitnessonline.model.enums.DifficultyLevel;
import com.example.fitnessonline.model.enums.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProgramRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private DifficultyLevel difficultyLevel;
    @NotNull
    private Integer duration;
    @NotNull
    private Location location;
    @NotBlank
    private String instructorName;
    @NotBlank
    private String instructorContact;
    private String videoUrl;
    @NotNull
    private Integer categoryId;
    @NotNull
    private Integer userCreatorId;
    private List<ImageRequest> images;
}
