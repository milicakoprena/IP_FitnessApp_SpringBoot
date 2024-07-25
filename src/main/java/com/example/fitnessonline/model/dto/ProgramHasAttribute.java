package com.example.fitnessonline.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramHasAttribute {
    private Integer id;
    private Program program;
    private Attribute attribute;
    private String value;
}
