package com.tempo.tempo.utils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    private static final String SECRET_KEY = "my_secret_yippee";
    private static final String ISSUER = "tempo";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(Long userId) {
        log.info("Generating token for user: " + userId);

        try {
            String token = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(userId.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000000)) // 20 seconds
                .sign(algorithm);

            return token;
        } catch (JWTCreationException exception){
            log.error("Error creating token");
            return null;
        }
    }

    public boolean verifyToken(String token) {
        Long userId = getUserIdFromToken(token);
        log.info("Verifying token for user with ID: " + userId);

        // Verify the JWT
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        try {
            DecodedJWT jwt = verifier.verify(token);
            log.info("Verified Token Subject: " + jwt.getSubject());
            return jwt.getSubject().equals(userId.toString());
        } catch (Exception e) {
            log.error("Invalid token: " + e.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return Long.valueOf(jwt.getSubject());
    }
}