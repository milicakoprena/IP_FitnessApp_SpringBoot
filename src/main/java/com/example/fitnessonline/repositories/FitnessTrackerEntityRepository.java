package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.FitnessTrackerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FitnessTrackerEntityRepository extends JpaRepository<FitnessTrackerEntity, Integer> {
    public List<FitnessTrackerEntity> getAllByUser_Id(Integer id);
}
