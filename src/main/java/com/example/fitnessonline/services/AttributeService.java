package com.example.fitnessonline.services;


import com.example.fitnessonline.model.dto.Attribute;

import java.util.List;

public interface AttributeService {
    List<Attribute> getAllByCategoryId(Integer id);
}
