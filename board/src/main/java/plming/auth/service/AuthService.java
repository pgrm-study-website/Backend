package plming.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public String loginWithEmail(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null || !bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return jwtTokenProvider.createToken(user.getId());
    }
}