package com.ajavacode.dev.eventbookingapp.service.security;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtService {

    String generateAccessJwt(String email);

    DecodedJWT verifyJwt(String token);
}
