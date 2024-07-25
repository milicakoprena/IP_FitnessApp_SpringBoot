package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.model.dto.Attribute;
import com.example.fitnessonline.model.dto.AttributeValue;
import com.example.fitnessonline.repositories.AttributeValueEntityRepository;
import com.example.fitnessonline.services.AttributeValueService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {
    private final ModelMapper modelMapper;
    private final AttributeValueEntityRepository attributeValueEntityRepository;

    public AttributeValueServiceImpl(ModelMapper modelMapper, AttributeValueEntityRepository attributeValueEntityRepository) {
        this.modelMapper=modelMapper;
        this.attributeValueEntityRepository=attributeValueEntityRepository;
    }

    @Override
    public List<AttributeValue> getAllByAttributeId(Integer id) {
        return attributeValueEntityRepository.getAllByAttribute_Id(id).stream().map(a->modelMapper.map(a, AttributeValue.class)).collect(Collectors.toList());

    }
}
