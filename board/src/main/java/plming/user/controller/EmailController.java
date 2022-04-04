//package plming.user.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import plming.user.service.EmailService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/email")
//public class EmailController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping("/send-code")
//    public ResponseEntity<String> emailAuth(HttpServletRequest request, @RequestBody Map<String, String> email) throws Exception {
//        HttpSession session = request.getSession();
//        if(emailService.sendSimpleMessage(session,email.get("email"))){
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    @PostMapping("/verify-code")
//    public ResponseEntity<?> verifyCode(HttpServletRequest request, @RequestBody Map<String, String> requestBody) {
//        HttpSession session = request.getSession();
//        String email = requestBody.get("email");
//        String code = requestBody.get("code");
//
//        if(emailService.certificateEmailCode(session,email,code)){
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }
//}