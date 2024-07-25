package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.UnauthorizedException;
import com.example.fitnessonline.model.dto.LoginResponse;
import com.example.fitnessonline.model.entities.UserEntity;
import com.example.fitnessonline.model.enums.UserStatus;
import com.example.fitnessonline.model.requests.AccountActivationRequest;
import com.example.fitnessonline.model.requests.LoginRequest;
import com.example.fitnessonline.repositories.UserEntityRepository;
import com.example.fitnessonline.services.AuthenticationService;
import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.util.LoggingUtil;
import com.example.fitnessonline.services.MailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value(value = "${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final UserEntityRepository userEntityRepository;
    private final MailService mailService;
    private final Map<String, String> pins = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);


    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserEntityRepository userEntityRepository, ModelMapper modelMapper, MailService mailService) {
        this.authenticationManager = authenticationManager;
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserEntity userEntity = userEntityRepository.findByUsername(request.getUsername()).orElseThrow(NotFoundException::new);
            if (userEntity.getStatus().equals(UserStatus.ACTIVE)) {
                LoginResponse response = modelMapper.map(userEntity, LoginResponse.class);
                response.setToken(generateJwt(userEntity));
                logger.info("LOGIN USER " + userEntity.getId());
                return response;
            } else {
                sendPinToMail(userEntity.getUsername(), userEntity.getMail());
                logger.info("LOGIN USER SEND PIN " + userEntity.getId());
                return null;
            }
        } catch (Exception e) {
            LoggingUtil.logException(e, getClass());
            throw new UnauthorizedException();
        }
    }

    private String generateJwt(UserEntity user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .claim("role", user.getStatus().name())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    @Override
    public void sendPinToMail(String username, String email){
        int pin;
        SecureRandom random = new SecureRandom();
        do {
            pin = random.nextInt(99999 + 1);
        } while (pins.containsValue(pin));

        pins.put(username, String.valueOf(pin));
        mailService.sendEmail(email, String.valueOf(pin));
    }


    @Override
    public boolean activateAccount(AccountActivationRequest request){
        return pins.containsKey(request.getUsername()) && pins.get(request.getUsername()).equals(request.getPin());
    }

    private Claims parseJWT(String token) {
        return Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token).getBody();
    }
}
