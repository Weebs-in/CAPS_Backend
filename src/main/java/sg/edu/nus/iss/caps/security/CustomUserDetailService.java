package sg.edu.nus.iss.caps.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sg.edu.nus.iss.caps.model.LoginUser;
import sg.edu.nus.iss.caps.service.UserService;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description: Load user specific data into Spring framework
 * @Date: Created at 9:32 2023/6/19
 * @Modified by:
 */

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = null;
        try {
            user = userService.getUserByUsername(username).orElseThrow(() -> new Exception("Bad credentials"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return UserPrincipal.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .build();
    }
}
