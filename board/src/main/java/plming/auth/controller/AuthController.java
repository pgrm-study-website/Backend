package plming.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import plming.auth.service.AuthService;
import plming.user.dto.UserJoinResponseDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
            Map<String,Object> resultMap = authService.loginWithEmail(email, password);
            response.addCookie(new Cookie("token",(String) resultMap.get("token")));
            return (UserJoinResponseDto)resultMap.get("responseDto");
        } else if (social > 0 && social < 4) {
            // 소셜 로그인
            if(social == 1){
                // 구글 oauth
            }else if(social == 2){
                // 카카오 oauth
            }else if(social == 3){
                // 깃허브 oauth
            }

        }
        return null;
    }

//    @PostMapping("/logout")
//    public void logout(HttpServletResponse response){
//        System.out.println("sdfsdfdsf");
//        Cookie cookie = new Cookie("token",null);
//        response.addCookie(cookie);
//        response.setStatus(204);
//    }
}