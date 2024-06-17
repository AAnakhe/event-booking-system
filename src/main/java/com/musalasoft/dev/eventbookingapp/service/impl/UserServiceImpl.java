package com.musalasoft.dev.eventbookingapp.service.impl;

import com.musalasoft.dev.eventbookingapp.dto.UserDto;
import com.musalasoft.dev.eventbookingapp.exceptions.UnauthorizedException;
import com.musalasoft.dev.eventbookingapp.exceptions.UserCreationException;
import com.musalasoft.dev.eventbookingapp.model.User;
import com.musalasoft.dev.eventbookingapp.repository.UserRepository;
import com.musalasoft.dev.eventbookingapp.service.UserService;
import com.musalasoft.dev.eventbookingapp.service.security.JwtService;
import com.musalasoft.dev.eventbookingapp.util.EntityBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final EntityBuilder builder;

    @Override
    public User createUser(UserDto userData) {
        try{
            User user = builder.buildUser(userData);
            return repository.save(user);
        } catch (Exception e){
            log.error("Error creating user {}", e.getMessage());
            throw new UserCreationException("Error creating user" + e.getMessage());
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found with email: " + email));
    }


}
