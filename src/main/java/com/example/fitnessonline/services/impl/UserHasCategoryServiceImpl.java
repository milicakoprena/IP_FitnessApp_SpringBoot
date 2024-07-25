package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.dto.UserHasCategory;
import com.example.fitnessonline.model.entities.UserHasCategoryEntity;
import com.example.fitnessonline.model.requests.UserHasCategoryRequest;
import com.example.fitnessonline.repositories.ProgramEntityRepository;
import com.example.fitnessonline.repositories.UserHasCategoryEntityRepository;
import com.example.fitnessonline.services.MailService;
import com.example.fitnessonline.services.UserHasCategoryService;
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
public class UserHasCategoryServiceImpl implements UserHasCategoryService {
    private final ModelMapper modelMapper;
    private final UserHasCategoryEntityRepository userHasCategoryEntityRepository;
    private final MailService mailService;
    private final ProgramEntityRepository programEntityRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LogManager.getLogger();

    public UserHasCategoryServiceImpl(ModelMapper modelMapper, UserHasCategoryEntityRepository userHasCategoryEntityRepository,
                                      MailService mailService, ProgramEntityRepository programEntityRepository) {
        this.modelMapper = modelMapper;
        this.userHasCategoryEntityRepository = userHasCategoryEntityRepository;
        this.mailService = mailService;
        this.programEntityRepository = programEntityRepository;
    }
    @Override
    public UserHasCategory findById(Integer id) {
        return modelMapper.map(userHasCategoryEntityRepository.findById(id).orElseThrow(NotFoundException::new), UserHasCategory.class);
    }

    @Override
    public UserHasCategory insert(UserHasCategoryRequest userHasCategoryRequest, Authentication auth) {
        UserHasCategoryEntity userHasCategoryEntity = modelMapper.map(userHasCategoryRequest, UserHasCategoryEntity.class);
        userHasCategoryEntity.setId(null);
        userHasCategoryEntity = userHasCategoryEntityRepository.saveAndFlush(userHasCategoryEntity);
        entityManager.refresh(userHasCategoryEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("USER " +jwtUser.getId() + " SUBSCRIBES TO CATEGORY " + userHasCategoryEntity.getCategory().getId());
        return findById(userHasCategoryEntity.getId());
    }

    @Override
    public boolean userHasCategory(Integer userId, Integer categoryId, Authentication auth) {
        return userHasCategoryEntityRepository.existsByUser_IdAndCategory_Id(userId,categoryId);

    }

    @Override
    public void delete(Integer userId, Integer categoryId, Authentication auth) {
        UserHasCategoryEntity userHasCategoryEntity = userHasCategoryEntityRepository
                .findByUser_IdAndCategory_Id(userId, categoryId);

        userHasCategoryEntityRepository.delete(userHasCategoryEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("USER " + jwtUser.getId() + " UNSUBSCRIBED FROM CATEGORY " + categoryId);
    }

    @Override
    public void sendNotifications() {
        userHasCategoryEntityRepository.findAll().stream().forEach(userHasCategoryEntity -> {
            String mail = userHasCategoryEntity.getUser().getMail();
            StringBuilder programs = new StringBuilder();

            programEntityRepository.getAllByCategory_Id(userHasCategoryEntity.getCategory().getId())
                    .stream().forEach(programEntity -> {
                        programs.append(programEntity.getTitle())
                                .append(" - ")
                                .append(programEntity.getDescription())
                                .append("\n");
                    });

            String notification = "This is the notification for the " + userHasCategoryEntity.getCategory().getName() + " category"
                    + "\nCurrent available programs are:\n" + programs.toString();
            mailService.sendNotification(mail, notification);
        });
    }


}
