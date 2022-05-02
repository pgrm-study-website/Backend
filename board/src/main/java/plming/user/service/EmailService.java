package plming.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.user.entity.UserRepository;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final String authAttributeName = "auth";

    // 메시지 생성.
    private MimeMessage createMessage(String email, String code)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();
        String content =
                "<div style= \"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #555; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"
                        +"<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"
                        +"<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Plming</span><br/>"
                        +"<span style=\"color: #555;\">메일인증코드</span> 안내입니다."
                        +"</h1>"
                        +"<p style=\"font-size: 16px; line-height: 26px; margin-top: 30px; padding: 0 5px;\">"
                        +"<b style=\"color: #555\">"+code+"</b>를 인증코드창에 기입하여 이메일 인증을 완료해주세요.<br/>"
                        +"</p>"
                        +"<div style=\"border-top: 1px solid #DDD; padding: 5px;\">"
                        +"<p style=\"font-size: 13px; line-height: 21px; color: #555;\">"
                        +"</p>"
                        +"</div>"
                        +"</div>";
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보내는 대상
        message.setSubject("Plming 확인 코드: " + code); //제목

        message.setText(content, "utf-8", "html"); //내용
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
            session.setAttribute(authAttributeName,false);
            return true;
        }catch(MailException es){
            return false;
        }
    }

    // 이메일 인증코드 체크
    public void certificateEmailCode(HttpSession session, String email, String inputCode){
        try{
            String originalCode = (String) session.getAttribute(email);
            if(!originalCode.equals(inputCode)){
                throw new CustomException(ErrorCode.BAD_REQUEST_EMAIL);
            }
            session.setAttribute(authAttributeName,true);
            session.setMaxInactiveInterval(300);
        }catch (Exception e){
            throw new CustomException(ErrorCode.BAD_REQUEST_EMAIL);
        }
    }

    // 이메일 인증여부를 확인
    public void certificateEmailAndAuth(HttpSession session, String email){
        // 입력받은 이메일로 인증절차를 진행하였는지 확인
        if(session.getAttribute(email) == null) throw new CustomException(ErrorCode.FORBIDDEN);
        // 이메일 인증여부 확인
        boolean isAuthed = (boolean)session.getAttribute(authAttributeName);
        if(!isAuthed) throw new CustomException(ErrorCode.EMAIL_AUTH_NOT_FOUND);
    }
}