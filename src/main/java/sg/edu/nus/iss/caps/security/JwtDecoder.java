package sg.edu.nus.iss.caps.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author: Cooper Liu
 * @Description: Decode a JWT token
 * @Date: Created at 20:08 2023/6/18
 * @Modified by:
 */

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;

    public DecodedJWT decode(String token) {
        return JWT
                // get secret
                .require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                // more requirements could be done here
                .build()
                .verify(token);
    }
}
