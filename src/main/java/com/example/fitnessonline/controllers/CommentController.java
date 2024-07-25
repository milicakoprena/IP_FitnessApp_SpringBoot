package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.Comment;
import com.example.fitnessonline.model.requests.CommentRequest;
import com.example.fitnessonline.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment insert(@RequestBody CommentRequest commentRequest, Authentication auth){
        return commentService.insert(commentRequest, auth);
    }
}
