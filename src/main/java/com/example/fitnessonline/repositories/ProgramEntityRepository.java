package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.ProgramEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramEntityRepository extends JpaRepository<ProgramEntity,Integer> {
    public List<ProgramEntity> getAllByCategory_Id(Integer id);
    public List<ProgramEntity> getAllByUserCreator_Id(Integer id);
    Page<ProgramEntity> findByTitleContainingIgnoreCase(String name, Pageable pageable);

}
