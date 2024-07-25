package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.Comment;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.entities.CommentEntity;
import com.example.fitnessonline.model.requests.CommentRequest;
import com.example.fitnessonline.repositories.CommentEntityRepository;
import com.example.fitnessonline.services.CommentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentEntityRepository commentEntityRepository;
    private static final Logger logger = LogManager.getLogger();
    @PersistenceContext
    private EntityManager entityManager;

    public CommentServiceImpl(ModelMapper modelMapper, CommentEntityRepository commentEntityRepository) {
        this.modelMapper = modelMapper;
        this.commentEntityRepository = commentEntityRepository;
    }

    @Override
    public Comment findById(Integer id) {
        return modelMapper.map(commentEntityRepository.findById(id).orElseThrow(NotFoundException::new), Comment.class);
    }

    @Override
    public Comment insert(CommentRequest commentRequest, Authentication auth) {
        CommentEntity commentEntity = modelMapper.map(commentRequest, CommentEntity.class);
        commentEntity.setId(null);
        commentEntity.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
        commentEntity = commentEntityRepository.saveAndFlush(commentEntity);
        entityManager.refresh(commentEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("INSERT COMMENT " + commentEntity.getId() + " BY USER " + jwtUser.getId());
        return findById(commentEntity.getId());
    }

    @Override
    public List<Comment> getAllByProgramId(Integer id) {
        return commentEntityRepository.getAllByProgram_Id(id).stream().map(a->modelMapper.map(a,Comment.class)).collect(Collectors.toList());
    }
}
