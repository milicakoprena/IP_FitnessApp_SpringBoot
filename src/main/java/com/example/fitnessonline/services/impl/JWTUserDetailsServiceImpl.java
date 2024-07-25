package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.UnauthorizedException;
import com.example.fitnessonline.model.dto.JWTUser;
import com.example.fitnessonline.repositories.UserEntityRepository;
import com.example.fitnessonline.services.JWTUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsServiceImpl implements JWTUserDetailsService {
    private final UserEntityRepository userRepository;
    private final ModelMapper modelMapper;

    public JWTUserDetailsServiceImpl(UserEntityRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userRepository.findByUsername(username).orElseThrow(UnauthorizedException::new), JWTUser.class);
    }
}