package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Integer> {
    public List<ImageEntity> getAllByProgram_Id(Integer id);
}
