package com.alperensertoglu.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.alperensertoglu.exception.UserManagerException;
import com.alperensertoglu.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    String secretKey = "secret-key";

    String issuer = "issuer";

    public Optional<String> createToken(Long id) {
        String token = null;
        Date date = new Date(System.currentTimeMillis() + (1000 * 60 * 5));
        try {
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("myId", id)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC512(secretKey));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(token);
    }

    public Optional<Long> getAuthIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new UserManagerException(ErrorType.INVALID_TOKEN);
            }
            Long id = decodedJWT.getClaim("myId").asLong();
            return Optional.of(id);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
    }


    public Optional<String> getRoleFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new UserManagerException(ErrorType.INVALID_TOKEN);
            }
            String role = decodedJWT.getClaim("role").asString();
            return Optional.of(role);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
    }
}
