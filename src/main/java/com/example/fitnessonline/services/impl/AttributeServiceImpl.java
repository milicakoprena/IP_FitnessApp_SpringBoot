package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.model.dto.Attribute;
import com.example.fitnessonline.model.dto.Comment;
import com.example.fitnessonline.repositories.AttributeEntityRepository;
import com.example.fitnessonline.services.AttributeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final ModelMapper modelMapper;
    private final AttributeEntityRepository attributeEntityRepository;

    public AttributeServiceImpl(ModelMapper modelMapper, AttributeEntityRepository attributeEntityRepository) {
        this.modelMapper = modelMapper;
        this.attributeEntityRepository = attributeEntityRepository;
    }

    @Override
    public List<Attribute> getAllByCategoryId(Integer id) {
        return attributeEntityRepository.getAllByCategory_Id(id).stream().map(a->modelMapper.map(a, Attribute.class)).collect(Collectors.toList());
    }
}
