package plming.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.user.entity.UserRepository;

@Service
@RequiredArgsConstructor
public class NicknameService {
    private final UserRepository userRepository;
    private final int ASCII_ALPHABET_START=65;
    private final String[] socialList = {"","구글","카카오","깃허브"};

    public String createRandomNickname(int social_type){
        if (social_type < 1) throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        StringBuilder nickname;
        do {
            nickname = new StringBuilder(socialList[social_type]);
            for (int i = 0; i < 5; i++){
                char alphabet = createRandomAlphabet();
                nickname.append(alphabet);
            }
        }while (userRepository.existsByNickname(nickname.toString()));
        return nickname.toString();
    }

    private char createRandomAlphabet(){
        int randomNumber = createRandomNumber();
        return (char)(ASCII_ALPHABET_START+randomNumber);
    }

    private int createRandomNumber(){
        return (int)(Math.random()*26);
    }
}
