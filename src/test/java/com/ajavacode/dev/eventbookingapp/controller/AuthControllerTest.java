package com.ajavacode.dev.eventbookingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ajavacode.dev.eventbookingapp.dto.Credentials;
import com.ajavacode.dev.eventbookingapp.model.User;
import com.ajavacode.dev.eventbookingapp.repository.UserRepository;
import com.ajavacode.dev.eventbookingapp.service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Credentials request;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void auth_returns200() throws Exception {
        Credentials payload = new Credentials("id3velope@gmail.com", "P@ssw0rd");
        Optional<User> user = userRepository.findByEmail(payload.email());

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(user);

        if (user.isPresent() && passwordEncoder.matches(payload.password(), user.get().getPassword())) {
            String token = jwtService.generateAccessJwt(payload.email());
            mockMvc.perform(post("/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(payload)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(true))
                    .andExpect(jsonPath("$.message").value("User authenticated successfully"))
                    .andExpect(jsonPath("$.data").value(token));
        }
    }

    @Test
    public void auth_returns401() throws Exception {
        Credentials payload = new Credentials("id3velope@gmail.com", "password");
        Optional<User> user = userRepository.findByEmail(payload.email());


        Mockito.when(userRepository.findByEmail(payload.email())).thenReturn(user);
        if (user.isEmpty() || !passwordEncoder.matches(payload.password(), user.get().getPassword())) {
            mockMvc.perform(post("/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(payload)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value(false))
                    .andExpect(jsonPath("$.message").exists());
        }
    }
}
