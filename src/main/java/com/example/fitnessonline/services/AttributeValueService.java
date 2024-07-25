package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.AttributeValue;

import java.util.List;

public interface AttributeValueService {
    List<AttributeValue> getAllByAttributeId(Integer id);
}
