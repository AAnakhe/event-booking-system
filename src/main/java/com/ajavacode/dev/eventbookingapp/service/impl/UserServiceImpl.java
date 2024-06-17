package com.ajavacode.dev.eventbookingapp.service.impl;

import com.ajavacode.dev.eventbookingapp.exceptions.UnauthorizedException;
import com.ajavacode.dev.eventbookingapp.exceptions.UserCreationException;
import com.ajavacode.dev.eventbookingapp.repository.UserRepository;
import com.ajavacode.dev.eventbookingapp.service.security.JwtService;
import com.ajavacode.dev.eventbookingapp.dto.UserDto;
import com.ajavacode.dev.eventbookingapp.model.User;
import com.ajavacode.dev.eventbookingapp.service.UserService;
import com.ajavacode.dev.eventbookingapp.util.EntityBuilder;
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
