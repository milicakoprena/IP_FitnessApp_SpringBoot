package com.example.fitnessonline.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    private Integer id;
    private String name;
    private Category category;
}
