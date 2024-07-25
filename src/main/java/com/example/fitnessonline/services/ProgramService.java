package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.model.requests.ProgramRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProgramService {
    List<Program> findAll();
    List<Program> getAllByCategoryId(Integer id);
    List<Program> getAllByUserCreatorId(Integer id);
    Program findById(Integer id);
    Program insert(ProgramRequest programRequest, Authentication auth);
    void delete(Integer id, Authentication auth);

    Page<Program> searchProgramsByTitle(String title, Pageable pageable);
}
