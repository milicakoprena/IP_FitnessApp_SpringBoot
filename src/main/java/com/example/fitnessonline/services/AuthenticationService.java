package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.LoginResponse;
import com.example.fitnessonline.model.requests.AccountActivationRequest;
import com.example.fitnessonline.model.requests.LoginRequest;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
    void sendPinToMail(String username, String email);
    boolean activateAccount(AccountActivationRequest request);
}
