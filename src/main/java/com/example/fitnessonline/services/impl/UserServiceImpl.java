package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.ConflictException;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.model.dto.User;
import com.example.fitnessonline.model.entities.UserEntity;
import com.example.fitnessonline.model.enums.UserStatus;
import com.example.fitnessonline.model.requests.PasswordRequest;
import com.example.fitnessonline.model.requests.SignUpRequest;
import com.example.fitnessonline.model.requests.UserRequest;
import com.example.fitnessonline.repositories.UserEntityRepository;
import com.example.fitnessonline.services.AuthenticationService;
import com.example.fitnessonline.services.UserService;
import com.example.fitnessonline.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LogManager.getLogger();

    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl(ModelMapper modelMapper, UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder,
                           AuthenticationService authenticationService) {
        this.modelMapper = modelMapper;
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @Override
    public void signUp(SignUpRequest request){
        if (userEntityRepository.findByUsername(request.getUsername()).isPresent())
            throw new ConflictException();
        UserEntity entity = modelMapper.map(request, UserEntity.class);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setStatus(UserStatus.REQUESTED);
        userEntityRepository.saveAndFlush(entity);
        authenticationService.sendPinToMail(entity.getUsername(), entity.getMail());
        logger.info("SIGNUP USER " + entity.getId());
    }

    @Override
    public boolean changePassword(Integer id, PasswordRequest passwordRequest, Authentication auth){
        UserEntity userEntity = modelMapper.map(userEntityRepository.findById(id),UserEntity.class);
        String oldPasswordEncoded = passwordEncoder.encode(passwordRequest.getOldPassword());
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        if (passwordEncoder.matches(passwordRequest.getOldPassword(), userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userEntity = userEntityRepository.saveAndFlush(userEntity);
            entityManager.refresh(userEntity);
            logger.info("USER CHANGED PASSWORD " + jwtUser.getId());
            return true;
        }
        logger.info("USER PASSWORD CHANGE FAILED" + jwtUser.getId());
        return false;
    }

    @Override
    public User findById(Integer id, Authentication auth) {
        return modelMapper.map(userEntityRepository.findById(id).orElseThrow(NotFoundException::new), User.class);
    }

    @Override
    public User update(Integer id, UserRequest userRequest, Authentication auth) {
        UserEntity userEntity = modelMapper.map(userRequest,UserEntity.class);
        userEntity.setId(id);
        userEntity.setStatus(userEntityRepository.findById(id).orElseThrow(NotFoundException::new).getStatus());
        userEntity.setUsername(userEntityRepository.findById(id).orElseThrow(NotFoundException::new).getUsername());
        userEntity.setPassword(userEntityRepository.findById(id).orElseThrow(NotFoundException::new).getPassword());
        userEntity = userEntityRepository.saveAndFlush(userEntity);
        entityManager.refresh(userEntity);
        JWTUser jwtUser = (JWTUser) auth.getPrincipal();
        logger.info("USER UPDATED" + jwtUser.getId());
        return findById(userEntity.getId(), auth);
    }

    @Override
    public User activateAccount(String username){
        UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        userEntity.setStatus(UserStatus.ACTIVE);
        logger.info("USER ACCOUNT ACTIVATED " + userEntity.getId());
        return modelMapper.map(userEntityRepository.saveAndFlush(userEntity), User.class);
    }

    @Override
    public List<User> findAll(Authentication auth) {
        return userEntityRepository.findAll().stream().map(a ->modelMapper.map(a, User.class)).collect(Collectors.toList());

    }
}
