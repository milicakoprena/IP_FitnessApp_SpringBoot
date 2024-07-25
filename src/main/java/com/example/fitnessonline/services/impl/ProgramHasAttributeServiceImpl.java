package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.dto.ProgramHasAttribute;
import com.example.fitnessonline.model.entities.ProgramHasAttributeEntity;
import com.example.fitnessonline.model.requests.ProgramHasAttributeRequest;
import com.example.fitnessonline.repositories.ProgramHasAttributeEntityRepository;
import com.example.fitnessonline.services.ProgramHasAttributeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramHasAttributeServiceImpl implements ProgramHasAttributeService {
    private final ModelMapper modelMapper;
    private final ProgramHasAttributeEntityRepository programHasAttributeEntityRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LogManager.getLogger();

    public ProgramHasAttributeServiceImpl(ModelMapper modelMapper, ProgramHasAttributeEntityRepository programHasAttributeEntityRepository) {
        this.modelMapper = modelMapper;
        this.programHasAttributeEntityRepository = programHasAttributeEntityRepository;
    }

    @Override
    public ProgramHasAttribute findById(Integer id) {
        return modelMapper.map(programHasAttributeEntityRepository.findById(id).orElseThrow(NotFoundException::new), ProgramHasAttribute.class);
    }

    @Override
    public ProgramHasAttribute insert(ProgramHasAttributeRequest programHasAttributeRequest, Authentication auth) {
        ProgramHasAttributeEntity programHasAttributeEntity = modelMapper.map(programHasAttributeRequest, ProgramHasAttributeEntity.class);
        programHasAttributeEntity.setId(null);
        programHasAttributeEntity = programHasAttributeEntityRepository.saveAndFlush(programHasAttributeEntity);
        entityManager.refresh(programHasAttributeEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("ATTRIBUTE " +programHasAttributeEntity.getValue() + " ADDED BY USER " + jwtUser.getId());
        return findById(programHasAttributeEntity.getId());
    }

    @Override
    public List<ProgramHasAttribute> getAllByProgramId(Integer id) {
        List<ProgramHasAttributeEntity> programHasAttributeEntities = programHasAttributeEntityRepository.getAllByProgram_Id(id);
        return programHasAttributeEntities.stream()
                .map(a->modelMapper.map(a, ProgramHasAttribute.class))
                .collect(Collectors.toList());
    }
}
