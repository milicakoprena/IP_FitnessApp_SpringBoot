package com.example.fitnessonline.model.dto;

import com.example.fitnessonline.model.enums.DifficultyLevel;
import com.example.fitnessonline.model.enums.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private DifficultyLevel difficultyLevel;
    private Integer duration;
    private Location location;
    private String instructorName;
    private String instructorContact;
    private String videoUrl;
    private Category category;
    private User userCreator;
    private List<Image> images;
    @JsonIgnore
    private List<Comment> comments;
}
