package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.UserHasProgram;
import com.example.fitnessonline.model.requests.UserHasProgramRequest;
import com.example.fitnessonline.services.UserHasProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_has_programs")
public class UserHasProgramController {
    private final UserHasProgramService userHasProgramService;

    public UserHasProgramController(UserHasProgramService userHasProgramService) {
        this.userHasProgramService = userHasProgramService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserHasProgram insert(@RequestBody UserHasProgramRequest userHasProgramRequest, Authentication auth){
        return userHasProgramService.insert(userHasProgramRequest, auth);
    }

    @Scheduled(cron = "0 24 15 * * ?")
    public void changeCompleted(){
        userHasProgramService.changeCompleted();
    }
}
