package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.User;
import com.example.fitnessonline.model.requests.AccountActivationRequest;
import com.example.fitnessonline.model.requests.SignUpRequest;
import com.example.fitnessonline.services.AuthenticationService;
import com.example.fitnessonline.services.UserService;
import com.example.fitnessonline.model.dto.LoginResponse;
import com.example.fitnessonline.model.requests.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService){
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("sign_up")
    public void signUp(@RequestBody @Valid SignUpRequest request){
        userService.signUp(request);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request){
        return authenticationService.login(request);
    }

    @PostMapping("activate")
    public User activateAccount(@RequestBody @Valid AccountActivationRequest request){
        if(authenticationService.activateAccount(request))
            return userService.activateAccount(request.getUsername());
        return null;
    }
}

