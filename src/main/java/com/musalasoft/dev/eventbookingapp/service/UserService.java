package com.musalasoft.dev.eventbookingapp.service;

import com.musalasoft.dev.eventbookingapp.dto.UserDto;
import com.musalasoft.dev.eventbookingapp.model.User;

public interface UserService {

    User createUser(UserDto userData);
    User findUserByEmail(String email);
}
