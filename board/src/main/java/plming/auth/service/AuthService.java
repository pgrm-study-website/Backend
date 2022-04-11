package plming.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.dto.UserJoinResponseDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    public UserJoinResponseDto loginWithEmail(String email, String password, HttpServletResponse response){
        User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.LOGIN_UNAUTHORIZED_EMAIL));
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_UNAUTHORIZED_PASSWORD);
        }
        String token = jwtTokenProvider.createToken(user.getId());
        jwtTokenProvider.setTokenInCookie(response,token);
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

    public void logout(HttpServletResponse response){
        jwtTokenProvider.deleteTokenInCookie(response);
    }

    public UserJoinResponseDto autoLogin(HttpServletRequest request){
        String token = jwtTokenProvider.validateTokenForAutoLogin(request);
        Long userId = jwtTokenProvider.getUserId(token);
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

//    public void loginWithGoogle(String code){
//        googleOauth.requestAccessToken(code);
//    }
}