package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.security.JwtIssuer;
import sg.edu.nus.iss.caps.model.LoginToken;
import sg.edu.nus.iss.caps.model.LoginUser;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 15:59 2023/6/18
 * @Modified by:
 */

@Tag(name = "Login API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final JwtIssuer jwtIssuer;

    @Operation(summary = "Login")
    @PostMapping("/login")
    public R login(@RequestBody LoginUser loginUser) {
        var token = jwtIssuer.issue(1L, loginUser.getUsername(), List.of("USER"));
        return R.ok(LoginToken.builder().accessToken(token).build());
    }

    @GetMapping("/secured")
    public R secured() {
        return R.ok("If you see this, then you're logged in.");
    }
}
