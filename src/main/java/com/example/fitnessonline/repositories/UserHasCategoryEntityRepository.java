package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.UserHasCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasCategoryEntityRepository extends JpaRepository<UserHasCategoryEntity, Integer> {
    boolean existsByUser_IdAndCategory_Id(Integer userId, Integer categoryId);

    UserHasCategoryEntity findByUser_IdAndCategory_Id(Integer userId, Integer categoryId);
}
