package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.model.LoginUser;
import sg.edu.nus.iss.caps.security.UserPrincipal;
import sg.edu.nus.iss.caps.service.AuthService;

/**
 * @Author: Cooper Liu
 * @Description: Auth Controller
 * @Date: Created at 15:59 2023/6/18
 * @Modified by:
 */

@Tag(name = "Authentication API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Login")
    @PostMapping("/login")
    public R login(@RequestBody LoginUser loginUser) {
        return authService.attemptLogin(loginUser.getUsername(), loginUser.getPassword());
    }
}
