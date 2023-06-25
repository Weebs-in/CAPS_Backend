package sg.edu.nus.iss.caps.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: Cooper Liu
 * @Description: A user principal class
 * @Date: Created at 19:59 2023/6/18
 * @Modified by:
 */

@Getter
@Builder
public class UserPrincipal implements UserDetails {

    private final Long userId;
    private final String username;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String displayRoles() {
        StringBuilder sb = new StringBuilder();
        for (GrantedAuthority role: authorities) {
            sb.append(role.toString());
        }
        return sb.toString();
    }
}
