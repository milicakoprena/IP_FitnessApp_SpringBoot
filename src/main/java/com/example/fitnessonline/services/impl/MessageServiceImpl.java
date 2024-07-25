package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.dto.Message;
import com.example.fitnessonline.model.entities.MessageEntity;
import com.example.fitnessonline.model.requests.MessageRequest;
import com.example.fitnessonline.repositories.MessageEntityRepository;
import com.example.fitnessonline.services.MessageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    private final ModelMapper modelMapper;
    private final MessageEntityRepository messageEntityRepository;

    public MessageServiceImpl(ModelMapper modelMapper, MessageEntityRepository messageEntityRepository) {
        this.modelMapper = modelMapper;
        this.messageEntityRepository = messageEntityRepository;
    }

    private static final Logger logger = LogManager.getLogger();
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Message insert(MessageRequest messageRequest, Authentication auth) {
        MessageEntity messageEntity = modelMapper.map(messageRequest, MessageEntity.class);
        messageEntity.setId(null);
        messageEntity.setIsRead(false);
        messageEntity = messageEntityRepository.saveAndFlush(messageEntity);
        entityManager.refresh(messageEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("INSERT MESSAGE " + messageEntity.getId() + " BY USER " + jwtUser.getId());
        return findById(messageEntity.getId());
    }

    @Override
    public Message findById(Integer id) {
        return modelMapper.map(messageEntityRepository.findById(id).orElseThrow(NotFoundException::new), Message.class);
    }
}
