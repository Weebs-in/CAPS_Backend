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

    @GetMapping("/secured")
    @PreAuthorize("hasAuthority('sys:admin')")
    public R secured(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return R.ok("If you see this, then you're logged in as admin: " + userPrincipal.getUsername()
                + ", user id: " + userPrincipal.getUserId() + ", your role: " + userPrincipal.displayRoles());
    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('sys:student')")
    public R student(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return R.ok("If you see this, then you're a student: " + userPrincipal.getUsername()
                + ", user id: " + userPrincipal.getUserId() + ", your role: " + userPrincipal.displayRoles());
    }
}
