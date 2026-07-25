package br.com.jardelcell.inventory.security;

import br.com.jardelcell.inventory.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtProperties jwtProperties;
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);
    private static final String ISSUER = "jardelcell-inventory-api";

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getEmail())
                .withExpiresAt(getExpiration())
                .sign(getAlgorithm());
    }

    public String validateToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            log.warn("Invalid JWT received.");
            return null;
        }
    }

    private Instant getExpiration() {
        return Instant.now()
                .plus(jwtProperties.expirationHours(), ChronoUnit.HOURS);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtProperties.secret());
    }
}
