package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.FitnessTracker;
import com.example.fitnessonline.model.requests.FitnessTrackerRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FitnessTrackerService {
    FitnessTracker insert(FitnessTrackerRequest fitnessTrackerRequest, Authentication auth);

    List<FitnessTracker> getAllFitnessTrackersByUserId(Integer id, Authentication auth);
}
