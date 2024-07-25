package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.Comment;
import com.example.fitnessonline.model.requests.CommentRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {
    Comment findById(Integer id);
    Comment insert(CommentRequest commentRequest, Authentication auth);
    List<Comment> getAllByProgramId(Integer id);
}
