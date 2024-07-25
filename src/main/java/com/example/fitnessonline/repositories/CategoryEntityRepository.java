package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.CategoryEntity;
import com.example.fitnessonline.model.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer> {

}
