package plming.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.AuthService;
import plming.user.dto.UserJoinResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/users")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UserJoinResponseDto login(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        int social = Integer.parseInt(requestBody.get("social"));
        if (social == 0) {
            // 이메일 로그인
            String email = requestBody.get("email");
            String password = requestBody.get("password");
            return authService.loginWithEmail(email, password, response);
        } else if (social > 0 && social < 4) {
            String code = requestBody.get("code");
            return authService.loginWithSocial(social,code, response);
        }
        return null;
    }

    @GetMapping("/check")
    public void autoLogin(HttpServletRequest request){
        authService.autoLogin(request);
    }
}