package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.UserHasCategory;
import com.example.fitnessonline.model.requests.UserHasCategoryRequest;
import com.example.fitnessonline.services.UserHasCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_has_categories")
public class UserHasCategoryController {
    private final UserHasCategoryService userHasCategoryService;

    public UserHasCategoryController(UserHasCategoryService userHasCategoryService) {
        this.userHasCategoryService = userHasCategoryService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserHasCategory insert(@RequestBody UserHasCategoryRequest userHasCategoryRequest, Authentication auth){
        return userHasCategoryService.insert(userHasCategoryRequest, auth);
    }

    @DeleteMapping("/{userId}/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId, @PathVariable Integer categoryId, Authentication auth) {
        userHasCategoryService.delete(userId, categoryId, auth);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/{categoryId}")
    public boolean userHasCategory(@PathVariable Integer userId, @PathVariable Integer categoryId, Authentication auth) {
        return userHasCategoryService.userHasCategory(userId, categoryId, auth);
    }

    @Scheduled(cron = "0 5 15 * * ?")
    public void sendNotifications(){
        userHasCategoryService.sendNotifications();
    }
}
