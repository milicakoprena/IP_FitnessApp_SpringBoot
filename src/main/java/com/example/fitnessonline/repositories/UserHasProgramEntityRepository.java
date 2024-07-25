package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.ProgramEntity;
import com.example.fitnessonline.model.entities.UserHasProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHasProgramEntityRepository extends JpaRepository<UserHasProgramEntity, Integer> {
    List<UserHasProgramEntity> getAllByUser_Id(Integer id);
    List<UserHasProgramEntity> getAllByUser_IdAndIsCompleted(Integer id, Boolean isCompleted);

    UserHasProgramEntity findByUser_IdAndProgram_Id(Integer userid, Integer programId);
    boolean existsByUser_IdAndProgram_Id(Integer userid, Integer programId);
}
