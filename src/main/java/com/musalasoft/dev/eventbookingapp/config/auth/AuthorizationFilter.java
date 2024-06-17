package com.musalasoft.dev.eventbookingapp.config.auth;

import com.musalasoft.dev.eventbookingapp.service.security.JwtService;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.musalasoft.dev.eventbookingapp.service.security.enums.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String HEADER_AUTHORIZATION = "Authorization";
        if (!Objects.isNull(request.getHeader(HEADER_AUTHORIZATION))) {
            String authorization = request.getHeader(HEADER_AUTHORIZATION);
            if (requestHasBearerInAuthorization(authorization)) {
                try {
                    authorizeRequestBearer(authorization);
                } catch (Exception e) {

                    if (resolver.resolveException(request, response, null, e) == null) {
                        log.error("Error occurred while authorizing user, cause: {}", e.getMessage());
                        throw e;
                    }
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }


    private void authorizeRequestBearer(String authorization) {

        String jwt = authorization.substring(7);
        DecodedJWT decodedJWT = jwtService.verifyJwt(jwt);
        String email = decodedJWT.getSubject();
        String tokenType = decodedJWT.getClaim("tokenType").asString();

        if (!TokenType.ACCESS.equals(TokenType.valueOf(tokenType)))return;

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        email, null, Collections.emptyList())
                );
        log.info(
                "Username [ {} ] authorized",
                email);

    }

    private Boolean requestHasBearerInAuthorization(String authorization) {
        return authorization.startsWith("Bearer ");
    }
}
