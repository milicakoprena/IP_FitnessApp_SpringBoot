package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.FitnessTracker;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.entities.FitnessTrackerEntity;
import com.example.fitnessonline.model.requests.FitnessTrackerRequest;
import com.example.fitnessonline.repositories.FitnessTrackerEntityRepository;
import com.example.fitnessonline.services.FitnessTrackerService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FitnessTrackerServiceImpl implements FitnessTrackerService {
    private final ModelMapper modelMapper;
    private final FitnessTrackerEntityRepository fitnessTrackerEntityRepository;
    private static final Logger logger = LogManager.getLogger();
    @PersistenceContext
    private EntityManager entityManager;

    public FitnessTrackerServiceImpl(ModelMapper modelMapper, FitnessTrackerEntityRepository fitnessTrackerEntityRepository, EntityManager entityManager) {
        this.modelMapper = modelMapper;
        this.fitnessTrackerEntityRepository = fitnessTrackerEntityRepository;
        this.entityManager = entityManager;
    }

    @Override
    public FitnessTracker insert(FitnessTrackerRequest fitnessTrackerRequest, Authentication auth) {
        FitnessTrackerEntity fitnessTrackerEntity = modelMapper.map(fitnessTrackerRequest, FitnessTrackerEntity.class);
        fitnessTrackerEntity.setId(null);
        fitnessTrackerEntity.setDate(Date.valueOf(LocalDate.now()));
        fitnessTrackerEntity = fitnessTrackerEntityRepository.saveAndFlush(fitnessTrackerEntity);
        entityManager.refresh(fitnessTrackerEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("INSERT FITNESS TRACKER " + fitnessTrackerEntity.getId() + " BY USER " + jwtUser.getId());
        return modelMapper.map(fitnessTrackerEntityRepository.findById(fitnessTrackerEntity.getId()).orElseThrow(NotFoundException::new), FitnessTracker.class);
    }

    @Override
    public List<FitnessTracker> getAllFitnessTrackersByUserId(Integer id, Authentication auth) {
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("GET ALL FITNESS TRACKERS BY USER ID " + jwtUser.getId());
        return fitnessTrackerEntityRepository.getAllByUser_Id(id).stream().map(a->modelMapper.map(a,FitnessTracker.class)).collect(Collectors.toList());
    }
}
