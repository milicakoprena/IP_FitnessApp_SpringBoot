package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeValueEntityRepository extends JpaRepository<AttributeValueEntity, Integer> {
    public List<AttributeValueEntity> getAllByAttribute_Id(Integer id);
}
