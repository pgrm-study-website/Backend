package plming.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import plming.auth.service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        int social = Integer.parseInt(requestBody.get("social"));
        if (social == 0) {
            // 이메일 로그인
            String email = requestBody.get("email");
            String password = requestBody.get("password");
            String token = authService.loginWithEmail(email, password);
            response.addCookie(new Cookie("token", token));
        } else if (social > 0 && social < 4) {
            // 소셜 로그인

        } else {
            // 에러 처리
        }
        return null;
    }
}