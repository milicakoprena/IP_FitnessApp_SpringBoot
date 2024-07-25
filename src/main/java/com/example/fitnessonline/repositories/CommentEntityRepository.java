package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentEntityRepository extends JpaRepository<CommentEntity,Integer> {
    public List<CommentEntity> getAllByProgram_Id(Integer id);
}
