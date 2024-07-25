package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.Message;
import com.example.fitnessonline.model.requests.MessageRequest;
import org.springframework.security.core.Authentication;

public interface MessageService {
    Message insert(MessageRequest messageRequest, Authentication auth);

    Message findById(Integer id);
}
