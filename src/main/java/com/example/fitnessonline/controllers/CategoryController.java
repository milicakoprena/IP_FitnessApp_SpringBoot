package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.Attribute;
import com.example.fitnessonline.model.dto.Category;
import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.services.AttributeService;
import com.example.fitnessonline.services.CategoryService;
import com.example.fitnessonline.services.ProgramService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProgramService programService;
    private final AttributeService attributeService;

    public CategoryController(ProgramService programService, AttributeService attributeService, CategoryService categoryService) {
        this.programService = programService;
        this.attributeService = attributeService;
        this.categoryService = categoryService;
    }
    @GetMapping
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}/programs")
    public List<Program> getAllProgramsByCategoryId(@PathVariable Integer id){
        return programService.getAllByCategoryId(id);
    }

    @GetMapping("/{id}/attributes")
    public List<Attribute> getAllAttributesByCategoryId(@PathVariable Integer id){
        return attributeService.getAllByCategoryId(id);
    }


}
