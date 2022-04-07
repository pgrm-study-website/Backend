package plming.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserJoinResponseDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    public Map<String,Object> loginWithEmail(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null || !bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_UNAUTHORIZED);
        }
        String token = jwtTokenProvider.createToken(user.getId());
        UserJoinResponseDto userJoinResponseDto = new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("token",token);
        resultMap.put("responseDto",userJoinResponseDto);
        return resultMap;
    }

//    public void loginWithGoogle(String code){
//        googleOauth.requestAccessToken(code);
//    }
}
