package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.ProgramHasAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramHasAttributeEntityRepository extends JpaRepository<ProgramHasAttributeEntity, Integer> {
    List<ProgramHasAttributeEntity> getAllByProgram_Id(Integer id);
}
