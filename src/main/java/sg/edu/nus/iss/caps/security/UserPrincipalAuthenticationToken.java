package sg.edu.nus.iss.caps.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 20:04 2023/6/18
 * @Modified by:
 */

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
    private final UserPrincipal userPrincipal;

    public UserPrincipalAuthenticationToken(UserPrincipal userPrincipal) {
        super(userPrincipal.getAuthorities());
        this.userPrincipal = userPrincipal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }
}
