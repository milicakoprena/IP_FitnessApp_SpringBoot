package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.AttributeEntity;
import com.example.fitnessonline.model.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeEntityRepository extends JpaRepository<AttributeEntity, Integer> {
    public List<AttributeEntity> getAllByCategory_Id(Integer id);
}
