package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.model.dto.UserHasProgram;
import com.example.fitnessonline.model.requests.UserHasProgramRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserHasProgramService {
    List<UserHasProgram> findAll();

    UserHasProgram findById(Integer id);
    List<Program> getAllProgramIdsByUserId(Integer id, Authentication auth);
    List<Program> getAllByUserIdAndCompleted(Integer id, Boolean isCompleted, Authentication auth);
    UserHasProgram insert(UserHasProgramRequest userHasProgramRequest, Authentication auth);
    boolean userHasProgram(Integer userId, Integer programId, Authentication auth);

    void changeCompleted();

    boolean userCompletedProgram(Integer userId, Integer programId, Authentication auth);
}
