package com.ajavacode.dev.eventbookingapp.service;

import com.ajavacode.dev.eventbookingapp.dto.UserDto;
import com.ajavacode.dev.eventbookingapp.model.User;

public interface UserService {

    User createUser(UserDto userData);
    User findUserByEmail(String email);
}
