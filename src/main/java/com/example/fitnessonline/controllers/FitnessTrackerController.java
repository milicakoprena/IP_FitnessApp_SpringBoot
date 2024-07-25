package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.FitnessTracker;
import com.example.fitnessonline.model.requests.FitnessTrackerRequest;
import com.example.fitnessonline.services.FitnessTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fitnesstrackers")
public class FitnessTrackerController {
    private final FitnessTrackerService fitnessTrackerService;

    public FitnessTrackerController(FitnessTrackerService fitnessTrackerService) {
        this.fitnessTrackerService = fitnessTrackerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FitnessTracker insert(@RequestBody FitnessTrackerRequest fitnessTrackerRequest, Authentication auth){
        return fitnessTrackerService.insert(fitnessTrackerRequest, auth);
    }
}
