package plming.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.user.service.EmailService;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-code")
    public ResponseEntity<String> emailAuth(@RequestBody Map<String, String> email) throws Exception {
        if(emailService.sendSimpleMessage(email.get("email"))){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("잘못된 이메일입니다.");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> code) {
        String email = code.get("email");
        return ResponseEntity.ok(emailService.ePw.contains(code.get("code")));
    }
}
