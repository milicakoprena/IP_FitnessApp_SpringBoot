package com.example.fitnessonline.services;
import com.example.fitnessonline.model.dto.User;
import com.example.fitnessonline.model.requests.PasswordRequest;
import com.example.fitnessonline.model.requests.SignUpRequest;
import com.example.fitnessonline.model.requests.UserRequest;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface UserService {
    void signUp(SignUpRequest request);

    boolean changePassword(Integer id, PasswordRequest passwordRequest, Authentication auth);

    User findById(Integer id, Authentication auth);
    User update(Integer id, UserRequest userRequest, Authentication auth);
    User activateAccount(String username);

    List<User> findAll(Authentication auth);
}
