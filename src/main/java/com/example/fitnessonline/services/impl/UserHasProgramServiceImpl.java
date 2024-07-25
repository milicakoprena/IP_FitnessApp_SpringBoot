package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.model.dto.UserHasProgram;
import com.example.fitnessonline.model.entities.UserHasProgramEntity;
import com.example.fitnessonline.model.requests.UserHasProgramRequest;
import com.example.fitnessonline.repositories.UserHasProgramEntityRepository;
import com.example.fitnessonline.services.UserHasProgramService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserHasProgramServiceImpl implements UserHasProgramService {
    private final ModelMapper modelMapper;
    private final UserHasProgramEntityRepository userHasProgramEntityRepository;

    public UserHasProgramServiceImpl(ModelMapper modelMapper, UserHasProgramEntityRepository userHasProgramEntityRepository) {
        this.modelMapper = modelMapper;
        this.userHasProgramEntityRepository = userHasProgramEntityRepository;
    }

    private static final Logger logger = LogManager.getLogger();
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserHasProgram> findAll() {
        return userHasProgramEntityRepository.findAll().stream().map(a ->modelMapper.map(a,UserHasProgram.class)).collect(Collectors.toList());
    }

    @Override
    public UserHasProgram findById(Integer id) {
        return modelMapper.map(userHasProgramEntityRepository.findById(id).orElseThrow(NotFoundException::new), UserHasProgram.class);
    }

    @Override
    public List<Program> getAllProgramIdsByUserId(Integer id, Authentication auth) {
        List<UserHasProgramEntity> userPrograms = userHasProgramEntityRepository.getAllByUser_Id(id);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("GET ALL THE PROGRAMS THE USER HAS BOUGHT " + jwtUser.getId());
        return userPrograms.stream()
                .map(UserHasProgramEntity::getProgram)
                .map(a->modelMapper.map(a,Program.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Program> getAllByUserIdAndCompleted(Integer id, Boolean isCompleted, Authentication auth) {
        List<UserHasProgramEntity> userPrograms = userHasProgramEntityRepository.getAllByUser_IdAndIsCompleted(id,isCompleted);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("GET ALL THE PROGRAMS THE USER HAS BOUGHT - COMPLETED = " + isCompleted + " " + jwtUser.getId());
        return userPrograms.stream()
                .map(UserHasProgramEntity::getProgram)
                .map(a->modelMapper.map(a,Program.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserHasProgram insert(UserHasProgramRequest userHasProgramRequest, Authentication auth) {
        UserHasProgramEntity userHasProgramEntity = modelMapper.map(userHasProgramRequest, UserHasProgramEntity.class);
        userHasProgramEntity.setId(null);
        userHasProgramEntity.setStartDate(Date.valueOf(LocalDate.now()));
        userHasProgramEntity.setIsCompleted(false);
        userHasProgramEntity = userHasProgramEntityRepository.saveAndFlush(userHasProgramEntity);
        entityManager.refresh(userHasProgramEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("USER " + jwtUser.getId() + " BUYS PROGRAM " + userHasProgramEntity.getProgram().getId());
        return findById(userHasProgramEntity.getId());
    }

    @Override
    public boolean userHasProgram(Integer userId, Integer programId, Authentication auth) {
        return userHasProgramEntityRepository.existsByUser_IdAndProgram_Id(userId,programId);
    }

    @Override
    public void changeCompleted() {
        List<UserHasProgram> allUsers = findAll();
        allUsers.forEach(userHasProgram -> {
            LocalDate start = LocalDate.parse(userHasProgram.getStartDate().toString());
            int duration = userHasProgram.getProgram().getDuration();
            LocalDate now = LocalDate.now();

            long daysBetween = ChronoUnit.DAYS.between(start, now);

            logger.info("User program start date: " + start);
            logger.info("Current date: " + now);
            logger.info("Program duration: " + duration + " days");
            logger.info("Days between start and now: " + daysBetween);

            if(daysBetween > duration){
                UserHasProgramEntity userHasProgramEntity = modelMapper.map(findById(userHasProgram.getId()), UserHasProgramEntity.class);
                userHasProgramEntity.setIsCompleted(true);
                userHasProgramEntity = userHasProgramEntityRepository.saveAndFlush(userHasProgramEntity);
                entityManager.refresh(userHasProgramEntity);
                logger.info("PROGRAM " + userHasProgram.getProgram().getId() + " COMPLETED BY USER " + userHasProgram.getUser().getId());
            }
        });
    }

    @Override
    public boolean userCompletedProgram(Integer userId, Integer programId, Authentication auth) {
        return userHasProgramEntityRepository.findByUser_IdAndProgram_Id(userId,programId).getIsCompleted();
    }


}
