package plming.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import plming.exception.CustomException;
import plming.exception.ErrorCode;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;

    // 메시지 생성
    private MimeMessage createMessage(String email, String code)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email); //보내는 대상
        message.setSubject("Plming 확인 코드: " + code); //제목

        message.setText(code, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("admin@plming.netfliy.com","plming")); //보내는 사람

        return message;
    }

    // 인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    // 메시지 전송
    public boolean sendSimpleMessage(HttpSession session, String email)throws Exception {
        try{
            String code = createKey();
            MimeMessage message = createMessage(email, code);
            emailSender.send(message);
            session.setAttribute(email,code);
            return true;
        }catch(MailException es){
            return false;
        }
    }

    // 이메일 인증코드 체크
    public void certificateEmailCode(HttpSession session, String email, String inputCode){
        try{
            String originalCode = (String) session.getAttribute(email);
<<<<<<< HEAD
            if(!originalCode.equals(inputCode)){
                throw new CustomException(ErrorCode.BAD_REQUEST_EMAIL);
            }
        }catch (Exception e){
            throw new CustomException(ErrorCode.BAD_REQUEST_EMAIL);
=======
            if(originalCode.equals(inputCode)){
                throw new CustomException(ErrorCode.BAD_REQUEST_EMAIL);
            }
        }catch (Exception e){
            throw new CustomException(ErrorCode.EMAIL_CODE_NOT_FOUND);
>>>>>>> 25a4201... Fix: 이메일 api 반환 코드 수정
        }
    }
}