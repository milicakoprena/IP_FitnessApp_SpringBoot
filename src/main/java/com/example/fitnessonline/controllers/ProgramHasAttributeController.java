package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.ProgramHasAttribute;
import com.example.fitnessonline.model.requests.ProgramHasAttributeRequest;
import com.example.fitnessonline.services.ProgramHasAttributeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/program_has_attributes")
public class ProgramHasAttributeController {
    private final ProgramHasAttributeService programHasAttributeService;

    public ProgramHasAttributeController(ProgramHasAttributeService programHasAttributeService) {
        this.programHasAttributeService = programHasAttributeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramHasAttribute insert(@RequestBody ProgramHasAttributeRequest programHasAttributeRequest, Authentication auth){
        return programHasAttributeService.insert(programHasAttributeRequest, auth);
    }
}
