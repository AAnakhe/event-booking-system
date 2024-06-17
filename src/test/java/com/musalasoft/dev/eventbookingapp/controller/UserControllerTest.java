package com.musalasoft.dev.eventbookingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musalasoft.dev.eventbookingapp.dto.Credentials;
import com.musalasoft.dev.eventbookingapp.dto.UserDto;
import com.musalasoft.dev.eventbookingapp.model.User;
import com.musalasoft.dev.eventbookingapp.repository.UserRepository;
import com.musalasoft.dev.eventbookingapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Credentials request;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        UserDto payload = new UserDto("Anakhe Ajayi", "id3velope@gmail.com", "P@ssw0rd");

        User user = new User();
        user.setId(1);
        user.setName(payload.name());
        user.setEmail(payload.email());
        user.setPassword(payload.password());
        userRepository.save(user);

        Mockito.when(userService.createUser(Mockito.any(UserDto.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.data.id").value(user.getId()))
                .andExpect(jsonPath("$.data.name").value(user.getName()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()));

    }


    @Test
    public void whenInvalidEmail_thenReturns400() throws Exception {
        UserDto payload = new UserDto("Anakhe Ajayi", "id3velope", "P@ssw0rd");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(false))
                        .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void whenPasswordIsInvalid_returns400() throws Exception {
        UserDto payload = new UserDto("Anakhe Ajayi", "Id3velope@gmail.com", "Password");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(false))
                        .andExpect(jsonPath("$.message").exists());
    }
}

