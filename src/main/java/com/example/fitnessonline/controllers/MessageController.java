package com.example.fitnessonline.controllers;


import com.example.fitnessonline.model.dto.Message;
import com.example.fitnessonline.model.requests.MessageRequest;
import com.example.fitnessonline.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message insert(@RequestBody MessageRequest messageRequest, Authentication auth){
        return messageService.insert(messageRequest, auth);
    }
}
