package com.example.fitnessonline.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValue {
    private Integer id;
    private String value;
    private Attribute attribute;
}
