package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.model.dto.Category;
import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.repositories.CategoryEntityRepository;
import com.example.fitnessonline.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryEntityRepository categoryEntityRepository;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryEntityRepository categoryEntityRepository) {
        this.modelMapper = modelMapper;
        this.categoryEntityRepository = categoryEntityRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryEntityRepository.findAll().stream().map(a ->modelMapper.map(a, Category.class)).collect(Collectors.toList());
    }
}
