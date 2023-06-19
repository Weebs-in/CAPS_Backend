package sg.edu.nus.iss.caps.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.model.LoginToken;
import sg.edu.nus.iss.caps.security.JwtIssuer;
import sg.edu.nus.iss.caps.security.UserPrincipal;

/**
 * @Author: Cooper Liu
 * @Description: Auth service provider
 * @Date: Created at 11:27 2023/6/19
 * @Modified by:
 */

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public R attemptLogin(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);
        return R.ok(LoginToken.builder().accessToken(token).build());
    }
}
