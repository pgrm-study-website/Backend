package plming.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import plming.auth.service.JwtTokenProvider;
import plming.exception.exception.CustomException;
import plming.exception.exception.ErrorCode;
import plming.user.dto.*;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto userJoinRequestDto) {
        userJoinRequestDto.setPassword(bCryptPasswordEncoder.encode(userJoinRequestDto.getPassword()));
        User user = userJoinRequestDto.toEntity();

        if(isEmailOverlap(user.getEmail())) throw new CustomException(ErrorCode.EMAIL_OVERLAP);
        if(isNickNameOverlap(user.getNickname())) throw new CustomException(ErrorCode.NICKNAME_OVERLAP);

        userRepository.save(user);
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

    public UserResponseDto getUserByNickName(String nickName) {
        User user = userRepository.findByNickname(nickName)
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        return new UserResponseDto(user);
    }

    public UserResponseDto getUserByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        return new UserResponseDto(user);
    }

    public UserResponseDto getUserByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        return new UserResponseDto(user);
    }

    public UserListResponseDto getUserList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONTENT));
        return new UserListResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateDto, HttpServletRequest request) {
//        jwtTokenProvider.validateTokenAndUserId(request,userUpdateDto.getId());
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        if(!user.getNickname().equals(userUpdateDto.getNickname()) && isNickNameOverlap(userUpdateDto.getNickname())){
            throw new CustomException(ErrorCode.NICKNAME_OVERLAP);
        }
        user.update(userUpdateDto);
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(user.getDeleteYn() == '1'){
            throw new CustomException(ErrorCode.ALREADY_DELETE);
        }
        user.delete();
    }

    public void checkPassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    @Transactional
    public void updatePassword(Long userId,String password) {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
        updateUser.updatePassword(bCryptPasswordEncoder.encode(password));
    }

    public boolean isEmailOverlap(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNickNameOverlap(String nickName){
        return userRepository.existsByNickname(nickName);
    }
}
