package plming.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import plming.auth.Oauth.service.OauthService;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.user.dto.UserJoinResponseDto;
import plming.user.dto.UserSocialJoinRequestDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import plming.user.service.NicknameService;
import plming.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final OauthService oauthService;
    private final UserService userService;
    private final NicknameService nicknameService;

    public UserJoinResponseDto loginWithEmail(String email, String password, HttpServletResponse response){
        User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.LOGIN_UNAUTHORIZED_EMAIL));
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_UNAUTHORIZED_PASSWORD);
        }
        String token = jwtTokenProvider.createToken(user.getId());
        jwtTokenProvider.setTokenInCookie(response,token);
        return new UserJoinResponseDto(user);
    }

    public UserJoinResponseDto loginWithSocial(int socialType, String authorizeCode, HttpServletResponse response){
        String userSocialId = oauthService.requestOauthUserId(socialType, authorizeCode);
        User user = userRepository.findBySocialAndSocialId(socialType,userSocialId).orElse(null);
        if(user == null){
            // 회원가입
            UserSocialJoinRequestDto userSocialJoinRequestDto = new UserSocialJoinRequestDto(
                    nicknameService.createRandomNickname(socialType),socialType,userSocialId);
            userService.createSocialUser(userSocialJoinRequestDto);
            user = userRepository.findBySocialAndSocialId(socialType,userSocialId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        }
        String token = jwtTokenProvider.createToken(user.getId());
        jwtTokenProvider.setTokenInCookie(response,token);
        return new UserJoinResponseDto(user);
    }

    public UserJoinResponseDto autoLogin(HttpServletRequest request){
        String token = jwtTokenProvider.validateTokenForAutoLogin(request);
        Long userId = jwtTokenProvider.getUserId(token);
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        return new UserJoinResponseDto(user);
    }
}