package com.musalasoft.dev.eventbookingapp.service.security.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.musalasoft.dev.eventbookingapp.exceptions.TokenVerificationException;
import com.musalasoft.dev.eventbookingapp.exceptions.UserNotFoundException;
import com.musalasoft.dev.eventbookingapp.repository.UserRepository;
import com.musalasoft.dev.eventbookingapp.service.security.JwtService;
import com.musalasoft.dev.eventbookingapp.service.security.enums.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserRepository repository;
    private static final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    @Override
    public String generateAccessJwt(String email) {
        repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Could not find user with email %s", email)
                ) );
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Instant expiresAt = tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return JWT.create()
                .withSubject(email)
                .withClaim("tokenType", TokenType.ACCESS.name())
                .withClaim("iat", Date.from(Instant.now()))
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }

    @Override
    public DecodedJWT verifyJwt(String token){
       try{
           JWTVerifier verifier = JWT.require(algorithm).build();
           return verifier.verify(token);
       }catch (Exception e){
           log.error("Error verifying token {}", e.getMessage());
           throw new TokenVerificationException(e.getMessage());
       }

    }

}
