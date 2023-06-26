package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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
    public R login(HttpSession session, @RequestBody LoginUser loginUser) {
        // 进行登录验证逻辑
        R response = authService.attemptLogin(loginUser.getUsername(), loginUser.getPassword());

        // 如果登录成功，将用户信息保存到会话中
        if (response.isOk()) {
            session.setAttribute("user", loginUser.getUsername());
        }

        return response;
    }

}


