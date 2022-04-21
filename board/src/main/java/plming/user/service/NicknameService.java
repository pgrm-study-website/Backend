package plming.user.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NicknameService {
    public String createRandomNickname(){
        return UUID.randomUUID().toString();
    }
}
