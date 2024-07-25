package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.Attribute;
import com.example.fitnessonline.model.dto.AttributeValue;
import com.example.fitnessonline.services.AttributeValueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributeController {
    private final AttributeValueService attributeValueService;

    public AttributeController(AttributeValueService attributeValueService) {
        this.attributeValueService = attributeValueService;
    }

    @GetMapping("/{id}/attributevalues")
    public List<AttributeValue> getAllAttributeValuesByAttribute_Id(@PathVariable Integer id){
        return attributeValueService.getAllByAttributeId(id);
    }
}
