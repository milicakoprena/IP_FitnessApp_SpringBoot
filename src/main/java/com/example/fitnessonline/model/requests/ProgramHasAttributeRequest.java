package com.example.fitnessonline.model.requests;

import com.example.fitnessonline.model.dto.Attribute;
import com.example.fitnessonline.model.dto.Program;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgramHasAttributeRequest {
    @NotNull
    private Integer programId;
    @NotNull
    private Integer attributeId;
    @NotBlank
    private String value;
}
