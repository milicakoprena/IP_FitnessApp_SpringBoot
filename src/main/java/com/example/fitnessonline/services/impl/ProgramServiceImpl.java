package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.model.entities.*;
import com.example.fitnessonline.model.requests.ProgramRequest;
import com.example.fitnessonline.repositories.*;
import com.example.fitnessonline.services.ProgramService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {
    private final ModelMapper modelMapper;
    private final ProgramEntityRepository programEntityRepository;
    private final ImageEntityRepository imageEntityRepository;
    private final CommentEntityRepository commentEntityRepository;
    private final UserHasProgramEntityRepository userHasProgramEntityRepository;
    private final ProgramHasAttributeEntityRepository programHasAttributeEntityRepository;
    private static final Logger logger = LogManager.getLogger();
    @PersistenceContext
    EntityManager entityManager;

    public ProgramServiceImpl(ModelMapper modelMapper, ProgramEntityRepository programEntityRepository,
                              ImageEntityRepository imageEntityRepository, CommentEntityRepository commentEntityRepository,
                              UserHasProgramEntityRepository userHasProgramEntityRepository,
                              ProgramHasAttributeEntityRepository programHasAttributeEntityRepository) {
        this.modelMapper = modelMapper;
        this.programEntityRepository = programEntityRepository;
        this.imageEntityRepository = imageEntityRepository;
        this.commentEntityRepository = commentEntityRepository;
        this.userHasProgramEntityRepository = userHasProgramEntityRepository;
        this.programHasAttributeEntityRepository = programHasAttributeEntityRepository;
    }

    @Override
    public List<Program> findAll() {
        return programEntityRepository.findAll().stream().map(a ->modelMapper.map(a,Program.class)).collect(Collectors.toList());
    }

    @Override
    public List<Program> getAllByCategoryId(Integer id) {
        return programEntityRepository.getAllByCategory_Id(id).stream().map(a ->modelMapper.map(a,Program.class)).collect(Collectors.toList());
    }

    @Override
    public List<Program> getAllByUserCreatorId(Integer id) {
        return programEntityRepository.getAllByUserCreator_Id(id).stream().map(a ->modelMapper.map(a,Program.class)).collect(Collectors.toList());
    }

    @Override
    public Program findById(Integer id) {
        return modelMapper.map(programEntityRepository.findById(id).orElseThrow(NotFoundException::new), Program.class);
    }

    @Override
    public Program insert(ProgramRequest programRequest, Authentication auth) {
        ProgramEntity programEntity = modelMapper.map(programRequest, ProgramEntity.class);
        programEntity.setId(null);
        programEntity = programEntityRepository.saveAndFlush(programEntity);
        for (ImageEntity imageEntity : programEntity.getImages()) {
            imageEntity.setId(null);
            imageEntity.setProgram(programEntity);
            imageEntityRepository.saveAndFlush(imageEntity);
            entityManager.refresh(imageEntity);
        }
        entityManager.refresh(programEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("INSERT PROGRAM " + programEntity.getId() + " BY USER " + jwtUser.getId());
        return findById(programEntity.getId());
    }
    @Override
    public void delete(Integer id, Authentication auth) throws NotFoundException{
        ProgramEntity programEntity = programEntityRepository.findById(id).orElseThrow(NotFoundException::new);
        for (ImageEntity imageEntity : programEntity.getImages()) {
            imageEntityRepository.deleteById(imageEntity.getId());
        }
        for (CommentEntity commentEntity : programEntity.getComments()) {
            commentEntityRepository.deleteById(commentEntity.getId());
        }
        for (UserHasProgramEntity userHasProgramEntity : programEntity.getUserHasPrograms()) {
            userHasProgramEntityRepository.deleteById(userHasProgramEntity.getId());
        }
        for (ProgramHasAttributeEntity programHasAttribute : programEntity.getProgramHasAttributes()) {
            programHasAttributeEntityRepository.deleteById(programHasAttribute.getId());
        }

        programEntityRepository.deleteById(id);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("PROGRAM " + programEntity.getId() + " DELETED BY USER " + jwtUser.getId());
    }

    @Override
    public Page<Program> searchProgramsByTitle(String title, Pageable pageable) {
        Page<ProgramEntity> programEntities = programEntityRepository.findByTitleContainingIgnoreCase(title , pageable);
        return programEntities.map(programEntity -> modelMapper.map(programEntity, Program.class));
    }


}
