package plming.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.user.service.EmailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/users/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-code")
    public ResponseEntity<String> emailAuth(HttpServletRequest request, @RequestBody Map<String, String> email) throws Exception {
        HttpSession session = request.getSession();
        if(emailService.sendSimpleMessage(session,email.get("email"))){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(HttpServletRequest request, @RequestBody Map<String, String> requestBody) {
        HttpSession session = request.getSession();
        String email = requestBody.get("email");
        String code = requestBody.get("code");
        emailService.certificateEmailCode(session,email,code);
        return ResponseEntity.ok().build();
    }
}