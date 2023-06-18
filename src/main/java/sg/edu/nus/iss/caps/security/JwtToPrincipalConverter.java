package sg.edu.nus.iss.caps.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description: Convert JWT to user principal
 * @Date: Created at 20:11 2023/6/18
 * @Modified by:
 */

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT decodedJWT) {
        return UserPrincipal.builder()
                .userId(Long.valueOf(decodedJWT.getSubject()))
                .username(decodedJWT.getClaim("u").asString())
                .authorities(extractedAuthorities(decodedJWT))
                .build();
    }

    private List<SimpleGrantedAuthority> extractedAuthorities(DecodedJWT jwt) {
        var claim = jwt.getClaim("a");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
