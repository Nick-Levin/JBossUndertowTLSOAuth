package com.c123.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.c123.model.User;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JWTUtil {

    private static final String SECRET = "secret";
    private static final String ISSUER = "c123";

    private JWTUtil() {}

    public static String createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        Map<String, String> payload = new HashMap<>();
        payload.put("username", user.getUsername());
        payload.put("email", user.getEmail());

        return JWT.create()
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(600)))
                .withIssuer(ISSUER)
                .withPayload(payload)
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        return verifier.verify(token);
    }

}
