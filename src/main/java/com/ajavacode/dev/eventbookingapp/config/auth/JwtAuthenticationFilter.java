package com.ajavacode.dev.eventbookingapp.config.auth;

import com.ajavacode.dev.eventbookingapp.exceptions.InvalidRequestException;
import com.ajavacode.dev.eventbookingapp.service.security.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ajavacode.dev.eventbookingapp.dto.Credentials;
import com.ajavacode.dev.eventbookingapp.util.ApiResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Credentials credentials;
    private final JwtService tokenManager;
    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            credentials = mapper.readValue(request.getReader(), Credentials.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    credentials.email(), credentials.password());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new InvalidRequestException("Invalid email or password");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        log.info(" with email {} successfully authenticated.", user.getUsername());
        response.setContentType("application/json; charset=UTF-8");
        String accessJwt = tokenManager.generateAccessJwt(user.getUsername());

        try (PrintWriter out = response.getWriter()) {

            ApiResponseDto apiResponseDto = new ApiResponseDto(
                    true,
                    "Signing Successful",
                    accessJwt
            );
            out.print(objectToJson(apiResponseDto));
        } catch (Exception e) {
            log.error("Print access jwt into  response body failed because {]", e.getCause());
        }
        response.setHeader("accessJwt", accessJwt);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        log.info("Authentication failed for user {} cause: {}", credentials.email(), failed.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        try (PrintWriter out = response.getWriter()) {
            ApiResponseDto apiResponseDto = new ApiResponseDto(
                    false,
                    "Failed to sign in, ensure email and password is correct",
                    null
            );
            out.println(objectToJson(apiResponseDto));
        }catch (Exception e){
            log.error("Failed to print to response body because {}", failed.getCause().getMessage());
        }
    }

    private String objectToJson(Object object) throws JsonProcessingException {
        if (object == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}