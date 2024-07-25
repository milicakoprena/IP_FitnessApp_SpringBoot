package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.UserHasCategory;
import com.example.fitnessonline.model.requests.UserHasCategoryRequest;
import org.springframework.security.core.Authentication;

public interface UserHasCategoryService {
    UserHasCategory findById(Integer id);
    UserHasCategory insert(UserHasCategoryRequest userHasCategoryRequest, Authentication auth);
    boolean userHasCategory(Integer userId, Integer categoryId, Authentication auth);

    void delete(Integer userId, Integer categoryId, Authentication auth);

    void sendNotifications();
}
