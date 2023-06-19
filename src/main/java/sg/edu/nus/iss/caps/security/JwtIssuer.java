package sg.edu.nus.iss.caps.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description: Issue a token for a valid user
 * @Date: Created at 19:14 2023/6/18
 * @Modified by:
 */

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties jwtProperties;
    public String issue(long userId, String userName, List<String> roles) {
        return JWT.create()
                // user id as subject
                .withSubject(String.valueOf(userId))
                // token expires after 1 day, change to 5 min later
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                // username
                .withClaim("u", userName)
                // authorizations
                .withClaim("a", roles)
                // set secret
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
