package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.*;
import com.example.fitnessonline.model.requests.PasswordRequest;
import com.example.fitnessonline.model.requests.UserRequest;
import com.example.fitnessonline.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ProgramService programService;
    private final UserHasProgramService userHasProgramService;
    private final FitnessTrackerService fitnessTrackerService;

    public UserController(UserService userService, ProgramService programService, UserHasProgramService userHasProgramService,
                          FitnessTrackerService fitnessTrackerService) {
        this.userService = userService;
        this.programService = programService;
        this.userHasProgramService = userHasProgramService;
        this.fitnessTrackerService = fitnessTrackerService;
    }

    @GetMapping
    public List<User> findAll(Authentication auth){
        return userService.findAll(auth);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id, Authentication auth){
        return userService.findById(id, auth);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, @RequestBody UserRequest userRequest, Authentication auth){
        return userService.update(id, userRequest, auth);
    }

    @PutMapping("/{id}/password")
    public boolean changePassword(@PathVariable Integer id, @RequestBody PasswordRequest passwordRequest, Authentication auth){
        return userService.changePassword(id, passwordRequest, auth);
    }

    @GetMapping("/{id}/my_programs")
    public List<Program> getAllProgramsByCreatorId(@PathVariable Integer id)
    {
        return programService.getAllByUserCreatorId(id);
    }
    @GetMapping("/{id}/programs")
    public List<Program> getAllProgramsByUserId(@PathVariable Integer id, Authentication auth) {
        return userHasProgramService.getAllProgramIdsByUserId(id, auth);
    }

    @GetMapping("/{userId}/userhasprogram/{programId}")
    public boolean userHasProgram(@PathVariable Integer userId, @PathVariable Integer programId, Authentication auth) {
        return userHasProgramService.userHasProgram(userId, programId, auth);
    }

    @GetMapping("/{userId}/usercompletedprogram/{programId}")
    public boolean userCompletedProgram(@PathVariable Integer userId, @PathVariable Integer programId, Authentication auth) {
        return userHasProgramService.userCompletedProgram(userId, programId, auth);
    }

    @GetMapping("/{id}/programs/{isCompleted}")
    public List<Program> filterProgramsByCompleted(@PathVariable Integer id,@PathVariable Boolean isCompleted, Authentication auth) {
        return userHasProgramService.getAllByUserIdAndCompleted(id,isCompleted, auth);
    }

    @GetMapping("/{id}/fitnesstrackers")
    public List<FitnessTracker> getAllFitnessTrackersByUserId(@PathVariable Integer id, Authentication auth) {
        return fitnessTrackerService.getAllFitnessTrackersByUserId(id, auth);
    }




}
