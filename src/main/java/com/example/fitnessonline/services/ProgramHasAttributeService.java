package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.ProgramHasAttribute;
import com.example.fitnessonline.model.requests.ProgramHasAttributeRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProgramHasAttributeService {
    ProgramHasAttribute findById(Integer id);
    ProgramHasAttribute insert(ProgramHasAttributeRequest programHasAttributeRequest, Authentication auth);
    List<ProgramHasAttribute> getAllByProgramId(Integer id);
}
