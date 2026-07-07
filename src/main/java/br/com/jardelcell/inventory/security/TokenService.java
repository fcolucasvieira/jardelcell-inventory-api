package br.com.jardelcell.inventory.security;

import br.com.jardelcell.inventory.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtProperties jwtProperties;

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer("jardelcell-inventory-api")
                .withSubject(user.getEmail())
                .withExpiresAt(getExpiration())
                .sign(getAlgorithm());
    }

    public String validateToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("jardelcell-inventory-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
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
