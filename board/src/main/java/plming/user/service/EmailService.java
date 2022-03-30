package plming.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    public static final String ePw = createKey();

    private MimeMessage createMessage(String to)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        String code = ePw;
        message.addRecipients(MimeMessage.RecipientType.TO, to); //보내는 대상
        message.setSubject("Plming 확인 코드: " + code); //제목

        String msg=code;

        message.setText(msg, "utf-8", "html"); //내용
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

    public boolean sendSimpleMessage(String email)throws Exception {
        MimeMessage message = createMessage(email);
        try{
            emailSender.send(message);
            return true;
        }catch(MailException es){
            return false;
        }
    }

}